package com.rpm.web.contents;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Lazy
public class CarsServiceImpl implements CarsService {
    @Autowired private CarsRepository carsRepository;

    @Override
    public Map<String, List<Cars>> getCategoryByCarType(Iterable<Cars> cars) {
        return StreamSupport.stream(cars.spliterator(), false)
                .filter(distinctByKey(Cars::getMakenm))
                .collect(Collectors.groupingBy(Cars::getCarType));
    }

    @Override
    public Map<String, Map<String, Long>> getCategory1(Iterable<Cars> cars) {
        return StreamSupport.stream(cars.spliterator(), false)
                .collect(Collectors.groupingBy(Cars::getCarType,
                        Collectors.groupingBy(Cars::getMakenm,Collectors.counting())));
    }
    @Override
    public Map<String, Long> getCategory2(String param) {
        return StreamSupport.stream(carsRepository.findByMakenm(param).spliterator(), false)
                .collect(Collectors.groupingBy(Cars::getModelGrpNm, Collectors.counting()));
    }
    @Override
    public Map<String, Long> getCategory3(String param) {
        return StreamSupport.stream(carsRepository.findByModelGrpNm(param).spliterator(), false)
                .collect(Collectors.groupingBy(Cars::getModelnmText, Collectors.counting()));
    }

    @Override
    public List<Cars>  findAllByDistinct(List<Cars> carsList) {
        return carsList.stream()
                .filter(distinctByKey(Cars::getCarcd))
                .collect(Collectors.toList());
    }

    @Override
    public List<Cars> findAllByDistinct(Iterable<Cars> cars) {
        return null;
    }

    @Override
    public <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    @Override
    public List<SearchDetailCondition> findByMakecdWithCount(List<Cars> carsList) {
        List<SearchDetailCondition> tmpMakecd = findByMakecd(carsList);
        for (SearchDetailCondition detailCondition : tmpMakecd) {
            int cnt = 0;
            for (Cars cars : carsList) {
                if (detailCondition.getCode().equals(cars.getMakecd())) cnt++;
            }
            detailCondition.setCount(cnt);
        }
        return tmpMakecd;
    }

    @Override
    public List<SearchDetailCondition> findByMakecd(List<Cars> carsList) {
        List<SearchDetailCondition> tmpMakecd = new ArrayList<>();
        for (Cars cars : carsList.stream()
                .filter(distinctByKey(Cars::getMakecd))
                .collect(Collectors.toList())) {
            SearchDetailCondition tmpCondition = new SearchDetailCondition();
            tmpCondition.setCode(cars.getMakecd());
            tmpCondition.setName(cars.getMakenm());
            tmpMakecd.add(tmpCondition);
        }
        return tmpMakecd;
    }

    @Override
    public List<SearchDetailCondition> findByModelWithCount(List<Cars> carsList , String code) {
        List<SearchDetailCondition> tmpModelcd = findByModelCategory(carsList , code);
        for (SearchDetailCondition detailCondition : tmpModelcd) {
            int cnt = 0;
            for (Cars cars : findCarBySelectedMaker( carsList , code ).stream()
                    .collect(Collectors.toList())) {
                if (detailCondition.getCode().equals(cars.getModelGrpCd())) cnt++;
            }
            detailCondition.setCount(cnt);
        }
        return tmpModelcd;
    }

    @Override
    public List<SearchDetailCondition> findByModelCategory(List<Cars> carsList, String code) {
        List<SearchDetailCondition> tmpModelGrpNm = new ArrayList<>();
        for (Cars cars : findCarBySelectedMaker(carsList,code).stream()
                .filter(distinctByKey(Cars::getModelGrpNm))
                .collect(Collectors.toList())) {
            SearchDetailCondition tmpCondition = new SearchDetailCondition();
            tmpCondition.setCode(cars.getModelGrpCd());
            tmpCondition.setName(cars.getModelGrpNm());
            tmpCondition.setBigCategory(cars.getMakecd());
            tmpModelGrpNm.add(tmpCondition);
        }
        return tmpModelGrpNm;
    }

    @Override
    public List<SearchDetailCondition> findByModelNMCategory(List<Cars> carsList, String name) {
        List<SearchDetailCondition> tmpModelGrpNm = new ArrayList<>();
        for (Cars cars : findCarBySelectedMakerNM( carsList , name ).stream()
                .filter(distinctByKey(Cars::getModelGrpCd))
                .collect(Collectors.toList())) {
            SearchDetailCondition tmpCondition = new SearchDetailCondition();
            tmpCondition.setCode(cars.getModelGrpCd());
            tmpCondition.setName(cars.getModelGrpNm());
            tmpCondition.setBigCategory(cars.getMakecd());
            tmpModelGrpNm.add(tmpCondition);
        }
        return tmpModelGrpNm;
    }

    @Override
    public List<Cars> findCarWithFuleType(List<Cars> carsList) {
        return carsList.stream()
                .filter(distinctByKey(Cars::getFuelTyped))
                .collect(Collectors.toList());
    }

    @Override
    public List<Cars> findCarWithCenterRegionCode(List<Cars> carsList) {
        return carsList.stream()
                .filter(distinctByKey(Cars::getCenterRegionCode))
                .collect(Collectors.toList());
    }

    @Override
    public List<Cars> findAllCategory(List<Cars> carsList) {
        return carsList.stream()
                .filter(distinctByKey(Cars::getCategorycd))
                .collect(Collectors.toList());
    }

    @Override
    public List<Cars> findCarBySelectedCategory(List<Cars> carsList, String categorycode) {
        return carsList.stream()
                .filter(cars -> categorycode.equals(cars.getCategorycd()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Cars> findCarBySelectedModel(List<Cars> carsList, String modelCode) {
        return carsList.stream()
                .filter(cars -> modelCode.equals(cars.getModelGrpCd()))
                .collect(Collectors.toList());
    }
    @Override
    public List<Cars> findCarBySelectedModelNM(List<Cars> carsList, String modelName) {
        return carsList.stream()
                .filter(cars -> modelName.equals(cars.getModelGrpNm()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Cars> findCarBySelectedFuelType(List<Cars> carsList, String fuelTypecode) {
        return carsList.stream()
                .filter(cars -> fuelTypecode.equals(cars.getFuelTyped()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Cars> findCarBySelectedRegion(List<Cars> carsList, String regioncode) {
        return carsList.stream()
                .filter(cars -> regioncode.equals(cars.getCenterRegionCode()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Cars> findCarBySelectedMaker(List<Cars> carsList, String code) {
        return carsList.stream()
                .filter(cars -> code.equals(cars.getMakecd()))
                .collect(Collectors.toList());
    }
    @Override
    public List<Cars> findCarBySelectedMakerNM(List<Cars> carsList, String name) {
        return carsList.stream()
                .filter(cars -> name.equals(cars.getMakenm()))
                .collect(Collectors.toList());
    }

    @Override
    public Map<String,Map<String,List<Cars>>> findMakerAndModelByModelText(String modelnmText) {
        return StreamSupport.stream(carsRepository.findByModelnmText(modelnmText).spliterator(), false)
                .collect(Collectors.groupingBy(Cars::getMakenm, Collectors.groupingBy(Cars::getModelGrpNm)));
    }


}
