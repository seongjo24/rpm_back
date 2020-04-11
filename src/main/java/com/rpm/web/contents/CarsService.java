package com.rpm.web.contents;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

@Component
@Lazy
public interface CarsService {
    Map<String, List<Cars>> getCategoryByCarType(Iterable<Cars> cars);

    Map<String, Map<String, Long>> getCategory1(Iterable<Cars> cars);

    List<Cars> findAllByDistinct(List<Cars> carsList);

    List<Cars> findAllByDistinct(Iterable<Cars> cars);

    <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor);

    Map<String, Long> getCategory2(String param);

    Map<String, Long> getCategory3(String param);

    List<SearchDetailCondition> findByMakecd(List<Cars> carsList);

    List<SearchDetailCondition> findByModelWithCount(List<Cars> carsList, String code);

    List<SearchDetailCondition> findByModelCategory(List<Cars> carsList, String code);

    List<SearchDetailCondition> findByModelNMCategory(List<Cars> carsList, String name);

    List<Cars> findCarWithFuleType(List<Cars> carsList);

    List<Cars> findCarWithCenterRegionCode(List<Cars> carsList);

    List<SearchDetailCondition> findByMakecdWithCount(List<Cars> carsList);

    List<Cars> findAllCategory(List<Cars> carsList);

    List<Cars> findCarBySelectedCategory(List<Cars> carsList, String category);

    List<Cars> findCarBySelectedMaker(List<Cars> carsList, String code);

    List<Cars> findCarBySelectedModelNM(List<Cars> carsList, String modelCode);

    List<Cars> findCarBySelectedFuelType(List<Cars> carsList, String code);

    List<Cars> findCarBySelectedRegion(List<Cars> carsList, String code);

    List<Cars> findCarBySelectedModel(List<Cars> carsList, String code);

    List<Cars> findCarBySelectedMakerNM(List<Cars> carsList, String name);

    Map<String,Map<String,List<Cars>>> findMakerAndModelByModelText(String modelnmText);

}
