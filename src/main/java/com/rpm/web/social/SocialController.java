package com.rpm.web.social;

import com.rpm.web.proxy.Box;
import com.rpm.web.proxy.PageProxy;
import com.rpm.web.user.User;
import com.rpm.web.user.UserRepository;
import com.rpm.web.util.PathEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin(origins = "http://kangsrpmdatabase.s3-website.ap-northeast-2.amazonaws.com", allowedHeaders = "*")
public class SocialController {
    @Autowired SocialRepository socialRepository;
    @Autowired UserRepository userRepository;
    @Autowired SocialService socialService;
    @Autowired PageProxy pager;
    @Autowired ThumbRepository thumbRepository;
    @Autowired Box box;


    @GetMapping("/viewList/{pageNo}/{userid}")
    public Map<String, Object> viewList(@PathVariable String pageNo, @PathVariable String userid){
        Map<String, Object> map = new HashMap<>();
        pager.setPageNum(Integer.parseInt(pageNo));
        pager.setPageSize(12);
        pager.paging(socialService.allList());
        int thisPageSize = (pager.getEndRow()+1)-((pager.getPageNum()-1)*pager.getPageSize());
        SocialListDTO[] list = new SocialListDTO[thisPageSize];
        List<SocialListDTO> param = socialService.allList();
        for(int i=0; i<thisPageSize; i++){
            list[i]=param.get(pager.getStartRow()+i);
        }
        map.put("boardList", list);
        if(!userid.equals("guest")){
            map.put("thumbedboard", socialService.thumbed(userid));
        }
        return map;
    }

    @PostMapping("/uploadImg")
    public String uploadImg(MultipartHttpServletRequest uploadFile) {
        //s3 업로드용
    /*Iterator<String> itr = uploadFile.getFileNames();
    String filename = itr.next();
    MultipartFile mfile = uploadFile.getFile(filename);
    String origName = mfile.getOriginalFilename();
    String directory = new SimpleDateFormat("yy-MM-dd").format(new Date()).replace("-", File.separator);
    String extension = origName.substring(origName.lastIndexOf(".") + 1);
    filename = UUID.randomUUID().toString() + "." + extension;


    AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
    amazonS3 =  AmazonS3ClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
            .withRegion(this.region)
            .build();
    box.add(directory);
    box.add(filename);
    System.out.println(box.get());
    if (amazonS3 != null) {
        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket + "\\img" + directory, filename, (File) mfile);
            putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
            amazonS3.putObject(putObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            amazonS3 = null;
        }
    }*/


        //local 파일 저장
    /*Iterator<String> itr =uploadFile.getFileNames();
    String filename = itr.next();
    MultipartFile mfile = uploadFile.getFile(filename);
    String origName=mfile.getOriginalFilename();
    String directory=new SimpleDateFormat("yy-MM-dd").format(new Date()).replace("-", File.separator);
    File serverPath = socialService.makeDir(PathEnum.UPLOAD_PATH.toString()+"\\img", directory);
    serverPath.mkdirs();
    String extension = origName.substring(origName.lastIndexOf(".")+1);
    filename = UUID.randomUUID().toString() +"."+extension;
    File serverFile = socialService.makeFile(serverPath, filename);
    box.add(directory);
    box.add(filename);
    System.out.println(box.get());
    try {
        mfile.transferTo(serverFile);
    } catch (Exception e) {
        e.printStackTrace();
    }*/


        return "uploadImg";   //filepond에 다시 전달하기 위해 String을 리턴함.
    }

    @DeleteMapping("/uploadImg")
    public String deleteUploadImg(HttpServletRequest uploadFile){
    /*Path file= Paths.get(PathEnum.UPLOAD_PATH.toString()+"\\img\\"
            +box.get().get(0)+File.separator+box.get().get(1));
    try {
        Files.delete(file);
    } catch (Exception e) {
        e.printStackTrace();
    }
    box.clear();*/
        return "";
    }

    @PostMapping("/writeContent")
    public Boolean writeContent (@RequestBody SocialWriteDTO param){
        socialService.writeContent(param);
        return true;
    }

    @GetMapping("/loadBoard/{boardSeq}")
    public SocialDetailDTO loadBoard(@PathVariable String boardSeq){
        return socialService.loadBoard(boardSeq);
    }

    @PostMapping("/updateContent/{boardSeq}")
    public Boolean updateContent (@PathVariable String boardSeq, @RequestBody SocialWriteDTO socialWriteDto){
        socialService.updateContent(boardSeq, socialWriteDto);
        return true;
    }
    @GetMapping("/deleteContent/{boardSeq}")
    public Boolean deleteContent(@PathVariable String boardSeq){
        socialService.deleteContent(boardSeq);
        return true;
    }

    @GetMapping("/thumbUp/{boardSeq}/{userid}")
    public Boolean thumbUp(@PathVariable String boardSeq, @PathVariable String userid){
        socialService.thumbUp(boardSeq, userid);
        return true;
    }
    @GetMapping("/thumbDown/{boardSeq}/{userid}")
    public Boolean thumbDown(@PathVariable String boardSeq, @PathVariable String userid){
        socialService.thumbDown(boardSeq, userid);
        System.out.println(userid);
        return true;
    }

    @GetMapping("/thumbed/{boardSeq}/{userid}")
    public Boolean thumbed(@PathVariable String boardSeq, @PathVariable String userid){
        Boolean result = false;
        Social social = socialRepository.findById(Long.parseLong(boardSeq)).get();
        User user = userRepository.findByUserid(userid);
        Thumb thumb = thumbRepository.findByBoardSeqAndUserSeq(social, user);
        if(thumb != null){result = true;}
        System.out.println(userid);
        return result;
    }

}
