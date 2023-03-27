package com.lth.cookingassistant.service.impl;

import java.util.Collection;

import javax.transaction.Transactional;

import com.lth.cookingassistant.exception.CANotFoundException;
import com.lth.cookingassistant.model.Account;
import com.lth.cookingassistant.model.Profile;
import com.lth.cookingassistant.model.RecipeReview;
import com.lth.cookingassistant.repo.AccountRepo;
import com.lth.cookingassistant.request.LoginRequest;
import com.lth.cookingassistant.service.AccountService;
import com.lth.cookingassistant.utils.EncyptPasswordUtiils;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class AccountServiceImpl implements AccountService {
	private final AccountRepo accountRepo;
	@Override
	public Account create(Account account) {
		log.info("Saving a new account: {}", account.getUsername());
		return accountRepo.save(account);
	}

	@Override
	public Collection<Account> list(int limit) {
		log.info("Fetching all accounts");
		return accountRepo.findAll(PageRequest.of(0, limit)).toList();
	}

	@Override
	public Collection<Account> list(int page, int size) {
		log.info(String.format("Fetching accounts from %d to %d", page, page * size));
		return accountRepo.findAll(PageRequest.of(page - 1, size)).toList();
	}


	@Override
	public Account get(Long id) {
		log.info("Fetching account by id: {}", id);
		return accountRepo.findById(id).get();
	}

	@Override
	public Account update(Account account) {
		log.info("Updating account: {}", account.getUsername());
		long id = account.getAccountId();

		if(!accountRepo.existsById(id)) {
			throw new CANotFoundException(
					new StringBuffer()
							.append("Account '")
							.append(id)
							.append("' not exist")
							.toString());
		}
		return accountRepo.save(account);
	}

	@Override
	public Account findByUsername(String username) {
		return accountRepo.findByUsername(username);
	}

	@Override
	public Account findByRecipeReviews(RecipeReview recipeReview) {
		return accountRepo.findByRecipeReviews(recipeReview);

	}

	@Override
	public Boolean delete(Long id) {
		log.info("Deleting account by id: {}", id);
		accountRepo.deleteById(id);
		return Boolean.TRUE;
	}

	@Override
	public Boolean isAccountExist(Long id) {
		return accountRepo.existsById(id);
	}

	@Override
	public Boolean isUsernameExist(String username) {
		return isAccountExist(findByUsername(username).getAccountId());
	}

	@Override
	public Boolean isSignInSuccess(LoginRequest loginForm) {
		Account account = accountRepo.findByUsername(loginForm.getUsername());
		Boolean result = true;
		if(isUsernameExist(loginForm.getUsername()))
			result = false;
		if(!EncyptPasswordUtiils.encyptPassword(loginForm.getPassword()).equals(account.getAccountId()))
			result = false;
		return result;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Kiểm tra xem user có tồn tại trong database không?
		Account account = accountRepo.findByUsername(username);
		if (account == null) {
			throw new UsernameNotFoundException(username);
		}
		return new Profile(account);
	}

	@Override
	public void changeAccountPassword(Account account, String newPassword) {
		account.setPassword(newPassword);
		accountRepo.save(account);
	}

	// JWTAuthenticationFilter sẽ sử dụng hàm này
	@Transactional
	public UserDetails loadUserById(Long id) {
		Account account = accountRepo.findById(id).orElseThrow(
				() -> new UsernameNotFoundException("User not found with id : " + id)
		);

		return new Profile(account);
	}
}
