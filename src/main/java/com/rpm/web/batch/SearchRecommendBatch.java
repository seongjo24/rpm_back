package com.rpm.web.batch;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.rpm.web.contents.Cars;
import com.rpm.web.contents.CarsRepository;
import com.rpm.web.contents.RecentSearchWord;
import com.rpm.web.contents.RecentSearchWordRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Configuration
@Component
public class SearchRecommendBatch {

    @Autowired
    JobBuilderFactory jobBuilderFactory;
    @Autowired
    StepBuilderFactory stepBuilderFactory;
    @Autowired
    RecentSearchWordRepository recentSearchWordRepository;
    @Autowired
    CarsRepository carsRepository;
    @Autowired
    JobLauncher jobLauncher;



    private List<Object> carModelList;
    private Table<String, String, Integer> searchHistory;
    private Table<String, String, Double> searchRecommend;

    public SearchRecommendBatch() {
        carModelList = new ArrayList<>();
        searchHistory = HashBasedTable.create();
        searchRecommend = HashBasedTable.create();
    }

    @Bean
    public Job searchRecoomendJob() {
        List<Cars> cars = (List<Cars>) carsRepository.findAll();
        carModelList.addAll(cars.stream().collect(Collectors.groupingBy(Cars::getModelnmText
                , Collectors.counting())).keySet());
        SimpleDateFormat SystemTime = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
        String formattedTime1 = SystemTime.format (System.currentTimeMillis());
        System.out.println( formattedTime1 + "  INFO 18844 --- [           Batch ]         : searchRecoomendJob Start ");
        return jobBuilderFactory.get("getSearchWordRecommend")
                .start(searchHistorySteb())
                .next(searchRecommendSteb())
                .build();
    }

    @Bean
    public Step searchHistorySteb() {
        return stepBuilderFactory.get("searchHistorySteb")
                .tasklet((contribution, chunkContext) -> {
                    Map<String, List<RecentSearchWord>> matrix = StreamSupport.stream(recentSearchWordRepository.findAll().spliterator(), false)
                            .collect(Collectors.groupingBy(RecentSearchWord::getUserId));
                    int count = 0;
                    if(matrix != null){
                    for(String user : matrix.keySet()){
                        for(Object model : carModelList){
                                count = 0;
                                for(RecentSearchWord recentSearchWord : matrix.get(user)){
                                    if(model.toString().equals(recentSearchWord.getSearchWord())){
                                        count=1;
                                    }
                                }
                                searchHistory.put( model.toString(), user, count);
                            }
                        }
                    }
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step searchRecommendSteb() {
        return stepBuilderFactory.get("searchRecommendSteb")
                .tasklet((contribution, chunkContext) -> {
                    SimpleDateFormat SystemTime = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
                    String formattedTime1 = SystemTime.format (System.currentTimeMillis());
                        for(String target :searchHistory.rowKeySet()){
                            Integer[] targetVal = searchHistory.row(target).values().toArray(new Integer[searchHistory.row(target).values().size()]);
                            for(String model :searchHistory.rowKeySet()){
                                long normA = 0, normB = 0, scla = 0;
                                if(!model.equals(target)){
                                    Integer[] listVal = searchHistory.row(model).values().toArray(new Integer[searchHistory.row(model).values().size()]);
                                    for(int i = 0; i<targetVal.length;i++){
                                        normA += (targetVal[i]*targetVal[i]);
                                        normB += (listVal[i]*listVal[i]);
                                        scla += (targetVal[i]*listVal[i]);
                                    }
                                }
                                searchRecommend.put(target,model, (scla!=0)?scla/(Math.sqrt(normA)*Math.sqrt(normB)):0);
                            }
                        }
                    System.out.println( formattedTime1 + "  INFO 18844 --- [           Batch ]         : searchRecoomendJob End ");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }



    @Scheduled(cron="0 0 4 * * *")
    public void perform() throws Exception
    {
        JobParameters params = new JobParametersBuilder()
                .addString("SeachRecommend", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();

        jobLauncher.run(searchRecoomendJob(), params);
    }

    public Map<String, Double> getSimilarities(String model){
        return searchRecommend.row(model);
    }

}
