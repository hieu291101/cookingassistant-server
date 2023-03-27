package com.lth.cookingassistant.service.impl;

import com.lth.cookingassistant.exception.CANotFoundException;
import com.lth.cookingassistant.model.Measure;
import com.lth.cookingassistant.repo.MeasureRepo;
import com.lth.cookingassistant.service.MeasureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class MeasureServiceImpl implements MeasureService {
    private final MeasureRepo measureRepo;

    @Override
    public Collection<Measure> list(int limit) {
        log.info("Fetching all measures");
        return measureRepo.findAll(PageRequest.of(0, limit)).toList();
    }

    @Override
    public Collection<Measure> searchMeasure(String keyword) {
        log.info("Fetching all measures");
        return measureRepo.findMeasuresByNameLike("%" + keyword + "%");
    }

    @Override
	public Collection<Measure> list(int page, int size) {
		log.info(String.format("Fetching measures from %d to %d", page, page * size));
		return measureRepo.findAll(PageRequest.of(page - 1, size)).toList();
	}

    @Override
    public Measure get(int id) {
        log.info("Fetching measure by id: {}", id);
        return measureRepo.findById(id).get();
    }

    @Override
    public long countMeasure() {
        return measureRepo.count();
    }

    @Override
    public Measure update(Measure measure) {
        log.info("Updating measure: {}", measure.getName());
        int id = measure.getMeasureId();

        if(!measureRepo.existsById(id)) {
            throw new CANotFoundException(
                    new StringBuffer()
                            .append("Measure '")
                            .append(id)
                            .append("' not exist")
                            .toString());
        }
        return measureRepo.save(measure);
    }

    @Override
    public Measure create(Measure measure) {
        log.info("Saving a new measure: {}", measure.getName());
        if(measureRepo.existsByName(measure.getName()))
            return null;

        return measureRepo.save(measure);
    }

    @Override
    public Boolean delete(int id) {
        log.info("Deleting measure by id: {}", id);
        measureRepo.deleteById(id);
        return Boolean.TRUE;
    }
}
