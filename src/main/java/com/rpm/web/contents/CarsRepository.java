package com.rpm.web.contents;


import org.springframework.context.annotation.Lazy;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Lazy
public interface CarsRepository extends CrudRepository<Cars, Long> {

    public List<Cars> findByMakenm(String makenm);
    public List<Cars> findByModelGrpNm(String modelGrpNm);
    public List<Cars> findByCenterCode(String centerCode);
    public List<Cars> findByModelnmText(String ModelnmText);
    public Cars findByCarcd(String carcd);
    public List<Cars> findByModelnmAndCenterCodeOrderByPrice(String mondelnm ,String centercode);
    public List<Cars> findByCategorycdAndCenterCode(String categorycd ,String centercode);
    public Cars  findFirstByModelnm(String modelnm);

}
