package org.zerock.guestbook.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.zerock.guestbook.dto.SampleRequestDTO;

import java.time.LocalDateTime;

@RestController
public class MappingController {


    @RequestMapping("/sample/not-value")
    @ResponseBody
    public String sample(){
        return "RequestMapping without value property";
    }

    @ResponseBody
    @RequestMapping("/sample/path/{param}")
    public String samplePath(@PathVariable("param")String parameter){
        return "Request Path Parameter : "+parameter;
    }
    @ResponseBody
    @RequestMapping("/sample/query-string")
    public String sampleQueryString(@RequestParam("param")String parameter){
        return "Sample Query String : "+parameter;
    }
    @ResponseBody
    @RequestMapping("/sample/query-string-many")
    public String sampleQueryString2(@RequestParam("param1")String parameter1,
                                     @RequestParam("param2")String parameter2){
        return "Many Query String : param 1 = " +parameter1 + "param2 =  "+parameter2;
    }
    @ResponseBody
    @RequestMapping("/sample/query-string-option")
    public String sampleQueryStringOption(@RequestParam(value = "required")String required,
                                          @RequestParam(value = "not_required", required = false)String notRequired,
                                          @RequestParam(value = "default_value",defaultValue = "default value")String defaultValue) {

        return String.format(
                "Query String Options. required=%s,notRequired=%s, defaultValue=%s",
                required, notRequired, defaultValue);
    }
    @ResponseBody
    @RequestMapping("/sample/withdout-request-param")
    public String sampleWithdoutRequestParam(String value){
        return value;
    }

    @ResponseBody
    @RequestMapping("/sample/param-dto")
    public String sampleParamDTO(SampleRequestDTO sampleRequestDTO){
        return sampleRequestDTO.toString();
    }

    @GetMapping("/sample/get-mapping")
    @ResponseBody
    public String sampleGetMapping(){
        return "this is get mapping example";
    }
    @PostMapping("/sample/post-mapping")
    @ResponseBody
    public String samplePostMapping(){
        return "this is post mapping example";
    }
    @GetMapping("/rest")
    public String index(){
        return "this is rest";
    }
    @GetMapping("/rest/now")
    public LocalDateTime now(){
        return LocalDateTime.now();
    }
    }

