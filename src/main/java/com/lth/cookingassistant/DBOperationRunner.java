package com.lth.cookingassistant;

import com.lth.cookingassistant.model.*;
import com.lth.cookingassistant.repo.AccountRepo;
import com.lth.cookingassistant.utils.EncyptPasswordUtiils;
import com.lth.cookingassistant.utils.GenericUtils;
import com.lth.cookingassistant.utils.RandomIdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;

@Component
public class DBOperationRunner implements CommandLineRunner {
    @Autowired
    AccountRepo accountRepo;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
//        accountRepo.saveAll(Arrays.asList(
//                new Account(RandomIdUtils.createId().longValue(), (byte) 0, "1", RandomIdUtils.createUUID().substring(1,10),new HashSet<Role>(), new HashSet<MyRecipe>(), new HashSet<Profile>(),new HashSet<Recipe>()),
//                new Account(RandomIdUtils.createId().longValue(), (byte) 0, "1", RandomIdUtils.createUUID().substring(1,10),new HashSet<Role>(), new HashSet<MyRecipe>(), new HashSet<Profile>(),new HashSet<Recipe>()),
//                new Account(RandomIdUtils.createId().longValue(), (byte) 0, "1", RandomIdUtils.createUUID().substring(1,10),new HashSet<Role>(), new HashSet<MyRecipe>(), new HashSet<Profile>(),new HashSet<Recipe>()),
//                new Account(RandomIdUtils.createId().longValue(), (byte) 0, "1", RandomIdUtils.createUUID().substring(1,10),new HashSet<Role>(), new HashSet<MyRecipe>(), new HashSet<Profile>(),new HashSet<Recipe>()),
//                new Account(RandomIdUtils.createId().longValue(), (byte) 0, "1", RandomIdUtils.createUUID().substring(1,10),new HashSet<Role>(), new HashSet<MyRecipe>(), new HashSet<Profile>(),new HashSet<Recipe>()),
//                new Account(RandomIdUtils.createId().longValue(), (byte) 0, "1", RandomIdUtils.createUUID().substring(1,10),new HashSet<Role>(), new HashSet<MyRecipe>(), new HashSet<Profile>(),new HashSet<Recipe>()),
//                new Account(RandomIdUtils.createId().longValue(), (byte) 0, "1", RandomIdUtils.createUUID().substring(1,10),new HashSet<Role>(), new HashSet<MyRecipe>(), new HashSet<Profile>(),new HashSet<Recipe>()),
//                new Account(RandomIdUtils.createId().longValue(), (byte) 0, "1", RandomIdUtils.createUUID().substring(1,10),new HashSet<Role>(), new HashSet<MyRecipe>(), new HashSet<Profile>(),new HashSet<Recipe>()),
//                new Account(RandomIdUtils.createId().longValue(), (byte) 0, "1", RandomIdUtils.createUUID().substring(1,10),new HashSet<Role>(), new HashSet<MyRecipe>(), new HashSet<Profile>(),new HashSet<Recipe>()),
//                new Account(RandomIdUtils.createId().longValue(), (byte) 0, "1", RandomIdUtils.createUUID().substring(1,10),new HashSet<Role>(), new HashSet<MyRecipe>(), new HashSet<Profile>(),new HashSet<Recipe>()),
//                new Account(RandomIdUtils.createId().longValue(), (byte) 0, "1", RandomIdUtils.createUUID().substring(1,10),new HashSet<Role>(), new HashSet<MyRecipe>(), new HashSet<Profile>(),new HashSet<Recipe>()),
//                new Account(RandomIdUtils.createId().longValue(), (byte) 0, "1", RandomIdUtils.createUUID().substring(1,10),new HashSet<Role>(), new HashSet<MyRecipe>(), new HashSet<Profile>(),new HashSet<Recipe>()),
//                new Account(RandomIdUtils.createId().longValue(), (byte) 0, "1", RandomIdUtils.createUUID().substring(1,10),new HashSet<Role>(), new HashSet<MyRecipe>(), new HashSet<Profile>(),new HashSet<Recipe>()),
//                new Account(RandomIdUtils.createId().longValue(), (byte) 0, "1", RandomIdUtils.createUUID().substring(1,10),new HashSet<Role>(), new HashSet<MyRecipe>(), new HashSet<Profile>(),new HashSet<Recipe>()),
//                new Account(RandomIdUtils.createId().longValue(), (byte) 0, "1", RandomIdUtils.createUUID().substring(1,10),new HashSet<Role>(), new HashSet<MyRecipe>(), new HashSet<Profile>(),new HashSet<Recipe>()),
//                new Account(RandomIdUtils.createId().longValue(), (byte) 0, "1", RandomIdUtils.createUUID().substring(1,10),new HashSet<Role>(), new HashSet<MyRecipe>(), new HashSet<Profile>(),new HashSet<Recipe>()),
//                new Account(RandomIdUtils.createId().longValue(), (byte) 0, "1", RandomIdUtils.createUUID().substring(1,10),new HashSet<Role>(), new HashSet<MyRecipe>(), new HashSet<Profile>(),new HashSet<Recipe>()),
//                new Account(RandomIdUtils.createId().longValue(), (byte) 0, "1", RandomIdUtils.createUUID().substring(1,10),new HashSet<Role>(), new HashSet<MyRecipe>(), new HashSet<Profile>(),new HashSet<Recipe>())
//        ));
//        Account user = new Account();
//        user.setUsername("hieu");
//        user.setPassword(passwordEncoder.encode("1"));
//        accountRepo.save(user);
        System.out.println(EncyptPasswordUtiils.encyptPassword("1"));
        System.out.println("----------All Data saved into Database----------------------");
    }
}
