package com.rpm.web.contents;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rpm.web.proxy.Proxy;
import com.rpm.web.util.MakeCarDummyList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Order(value=0)
@Component
public class CarsInit implements ApplicationRunner {
    @Autowired
    Proxy proxy;
    private CarsRepository carsRepository;
    private RecentSearchWordRepository recentSearchWord;
    private List<String> list;

    public CarsInit(CarsRepository carsRepository, RecentSearchWordRepository recentSearchWord) {
        this.carsRepository = carsRepository;
        this.recentSearchWord = recentSearchWord;
        this.list = new ArrayList<>();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        System.out.println( new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss").format (System.currentTimeMillis()) + "  INFO 18844 --- [           CarsInit ]         : CarsInit Start ");


        MakeCarDummyList http = new MakeCarDummyList();
        ObjectMapper jsonMapper = new ObjectMapper();
        String[] json = null;
        Map<String, Map<String, Object>> map = new HashMap<>();

        if (carsRepository.count() == 0) {
            int count = Integer.parseInt(http.getCarCount("https://www.kcar.com/index_api/getCarCnt.do"));
            for (int i = 1; i <= count-100; i++) {
                if ( i % 1000 == 0 ) System.out.println( new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss").format (System.currentTimeMillis()) + "  INFO 18844 --- [           CarsInit ]         : CarsInit processing :: insert Data count = [ "+ i +" ] ");

                Map<String, String> strJson = new HashMap<>();
                map = jsonMapper.readValue(
                        http.sendPost("https://www.kcar.com/search/api/getCarSearchWithCondition.do", proxy.string(i))
                                .replace("=", ":"), Map.class);
                json = map.get("result").get("rows").toString()
                        .replace("[{", "").replace("}]", "").split(",");
                for (int j = 0; j < json.length; j++) {
                    if (json[j].indexOf("=") + 1 >= 1) {
                        strJson.put(json[j].substring(0, json[j].indexOf("=")).trim(), json[j].substring(json[j].indexOf("=") + 1));
                    }
                }
                if(!list.contains(strJson.get("v_modelnm_text"))){
                    list.add(strJson.get("v_modelnm_text"));
                }

                carsRepository.save(new Cars(strJson.get("v_carcd"),
                        strJson.get("v_optioncd"), strJson.get("v_categorycd"), strJson.get("v_center_code"),
                        strJson.get("v_middle_img"), strJson.get("v_elan_path"), strJson.get("v_exterior_colorcd"),
                        strJson.get("v_center_region"), strJson.get("v_ecc_reg_dtm"),
                        (strJson.get("v_begin_year")==null)?0:Integer.parseInt(strJson.get("v_begin_year")),
                        strJson.get("v_makecd"), strJson.get("v_small_img"), strJson.get("v_modelnm"),
                        strJson.get("v_optioncd_name"), strJson.get("v_car_type"), strJson.get("v_pn_mobile"),
                        strJson.get("v_usernm"),
                        (strJson.get("n_mileage")==null)?0:Integer.parseInt(strJson.get("n_mileage")),
                        strJson.get("v_simple_comment"),
                        strJson.get("v_modelnm_text"), strJson.get("v_transmissioncd"), strJson.get("v_car_number"),
                        (strJson.get("n_price")==null)?0:Integer.parseInt(strJson.get("n_price")),
                        strJson.get("v_fuel_typecd_name"), strJson.get("v_car_url"),
                        strJson.get("v_fuel_typecd"), strJson.get("v_center_region_code"), strJson.get("v_truck_name"),
                        strJson.get("v_categorynm"), strJson.get("v_exterior_colornm"), strJson.get("v_hot_markcd"),
                        strJson.get("v_transmissioncd_name"), strJson.get("n_pass_cnt"), strJson.get("v_modelcd"),
                        strJson.get("v_rec_comment_cd"), strJson.get("v_makenm"), strJson.get("v_pn"),
                        strJson.get("v_mfr_date"), strJson.get("v_model_grp_cd"), strJson.get("v_center_name"),
                        strJson.get("v_model_grp_nm")
                ));
            }
        }
        if(recentSearchWord.count()==0){
            for(int i =0; i<300; i++){
                for(int j = 0; j<100; j++){
                    recentSearchWord.save(new RecentSearchWord(list.get((int)(Math.random() * list.size())), Long.parseLong("20200000000000000"), String.valueOf(i)));
                }
            }

        }


        System.out.println( new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss").format (System.currentTimeMillis()) + "  INFO 18844 --- [           CarsInit ]         : CarsInit End ");

    }
}
