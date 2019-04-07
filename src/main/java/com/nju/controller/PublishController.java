package com.nju.controller;

import com.nju.dao.ProductPublishDao;
import com.nju.enums.ResultMessage;
import com.nju.model.PackagePublish;
import com.nju.model.ProductPublish;
import com.nju.model.ResDiscount;
import com.nju.service.PublishService;
import com.nju.vo.PackagePublishVO;
import com.nju.vo.ProductPublishVO;
import com.nju.vo.ResDiscountVO;
import com.nju.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/publish")
public class PublishController {

    @Autowired
    private PublishService publishService;

    /**
     * 发布单品
     * @return
     */
    @PostMapping(value = "/publishProduct")
    public ResultVO publishProduct(@RequestBody ProductPublishVO productPublishVO){
        ProductPublish productPublish = new ProductPublish(productPublishVO.getResId(),productPublishVO.getName(),productPublishVO.getPrice(),productPublishVO.getNumber(),productPublishVO.getDescription(),productPublishVO.getImage(),productPublishVO.getDate(),productPublishVO.getStartTime(),productPublishVO.getEndTime());
        List list = publishService.publishProduct(productPublish);
        ResultMessage resultMessage = (ResultMessage) list.get(0);

        if(resultMessage == ResultMessage.SUCCESS){
            Long id = (Long)list.get(1);
            return new ResultVO(resultMessage.getCode(),"发布成功", id);
        } else {
            return new ResultVO(resultMessage.getCode(),"发布失败", null);
        }
    }

    /**
     * 通过餐厅Id查找餐厅所有已发布的单品
     * @return
     */
    @GetMapping(value = "/{resId}/getProducts")
    public ResultVO findProductPublihshByResId(@PathVariable("resId") Long resId,
                                               @RequestParam("date") String date,
                                               @RequestParam("startTime") String startTime,
                                               @RequestParam("endTime") String endTime){
//        System.out.println("开始运行");
//        System.out.println(resId);
//        System.out.println(date);
//        System.out.println(startTime);
//        System.out.println(endTime);
        List<ProductPublish> productPublishes = publishService.getFitProductsByResIdAndTime(resId,date,startTime,endTime);
        List<ProductPublishVO> productPublishVOS = new ArrayList<>();
        for(ProductPublish productPublishes1 : productPublishes){
            productPublishVOS.add(productPublishToVO(productPublishes1));
        }
        if(productPublishVOS != null){
            return new ResultVO(ResultMessage.SUCCESS.getCode(),ResultMessage.SUCCESS.getValue(),productPublishVOS);
        }
        return new ResultVO(ResultMessage.FAILED.getCode(),ResultMessage.FAILED.getValue(),null);
    }

    /**
     * 发布套餐
     * @param packagePublishVO
     * @return
     */
    @PostMapping(value = "/publishPackage")
    public ResultVO publishPackage(@RequestBody PackagePublishVO packagePublishVO){
        List<String> productNames = packagePublishVO.getProductNames();
        List<Long> productIds = new ArrayList<>();
        for(String productName : productNames){
            Long id = publishService.findProductIdByProductNameAndResId(productName,packagePublishVO.getResId(),packagePublishVO.getDate(),packagePublishVO.getNumber());
            productIds.add(id);
        }
        PackagePublish packagePublish = new PackagePublish(packagePublishVO.getResId(),packagePublishVO.getName(),productIds,packagePublishVO.getPrice(),packagePublishVO.getNumber(),packagePublishVO.getDescription(),packagePublishVO.getImage(),packagePublishVO.getDate(),packagePublishVO.getStartTime(),packagePublishVO.getEndTime());
        List list = publishService.publishPackage(packagePublish);
        ResultMessage resultMessage = (ResultMessage) list.get(0);
        if(resultMessage == ResultMessage.SUCCESS){
            Long id = (Long)list.get(1);
            return new ResultVO(resultMessage.getCode(),"发布成功", id);
        } else {
            return new ResultVO(resultMessage.getCode(),"发布失败", null);
        }
    }

    /**
     * 餐厅得到今日所有发布的单品
     * @param resId
     * @param search
     * @return
     */
    @GetMapping(value = "/getProInfos/{resId}")
    public ResultVO getSearchProInfos(@PathVariable("resId") Long resId,
                                       @RequestParam("search") String search){
        List<ProductPublishVO> productPublishVOS = new ArrayList<>();
        List<ProductPublish> productPublishes;
        if(search.equals("")){  //搜索框未输入
            productPublishes = publishService.getDateFitPros(resId);
        }else{  //搜索框输入
            productPublishes = publishService.getDateAndSearchFitPros(resId,search);
        }

        for(ProductPublish productPublish:productPublishes){
            productPublishVOS.add(productPublishToVO(productPublish));
        }
        System.out.println(productPublishVOS.size());
        return new ResultVO(ResultMessage.SUCCESS.getCode(),ResultMessage.SUCCESS.getValue(),productPublishVOS);
    }

    /**
     * 根据餐厅id和搜索得到当日套餐信息
     * @param resId
     * @param search
     * @return
     */
    @GetMapping(value = "/getPacInfos/{resId}")
    public ResultVO getSearchPacInfos(@PathVariable("resId") Long resId,
                                      @RequestParam("search") String search){
        List<PackagePublishVO> packagePublishVOS = new ArrayList<>();
        List<PackagePublish> packagePublishes;
        if(search.equals("")){  //搜索框未输入
            packagePublishes = publishService.getDateFitPacs(resId);
        }else{  //搜索框输入
            packagePublishes = publishService.getDateAndSearchFitPacs(resId,search);
        }

        for(PackagePublish packagePublish:packagePublishes){
            List<String> names = new ArrayList<>();
            for(Long id : packagePublish.getProductIds()){
                names.add(publishService.getProNameByProId(id));
            }
            packagePublishVOS.add(packagePublishToVO(packagePublish,names));
        }
        return new ResultVO(ResultMessage.SUCCESS.getCode(),ResultMessage.SUCCESS.getValue(),packagePublishVOS);
    }

    /**
     * 发布套餐
     * @param resDiscountVO
     * @return
     */
    @PostMapping(value = "/publishDiscount")
    public ResultVO publishDiscount(@RequestBody ResDiscountVO resDiscountVO){
        List list = resDiscountVO.getFullReduction();
        Map<Double,Double> map = new HashMap<>();
        for(int i = 0;i<list.size();i++){
            List list1 = (List)list.get(i);
            map.put(Double.valueOf((String)list1.get(0)),Double.valueOf((String)list1.get(1)));
        }
        ResDiscount resDiscount = new ResDiscount(resDiscountVO.getResId(),resDiscountVO.getDate(),map,resDiscountVO.getNewUserInResReduction());
        ResultMessage resultMessage = publishService.publishResDiscount(resDiscount);
        return new ResultVO(resultMessage.getCode(),resultMessage.getValue(),null);
    }

    /**
     * ProductPublish model转vo
     * @param productPublish
     * @return
     */
    private static ProductPublishVO productPublishToVO(ProductPublish productPublish){
        return new ProductPublishVO(productPublish.getId(),productPublish.getResId(),productPublish.getName(),productPublish.getPrice(),productPublish.getNumber(),productPublish.getDescription(),productPublish.getImage(),productPublish.getDate(),productPublish.getStartTime(),productPublish.getEndTime());
    }

    private static PackagePublishVO packagePublishToVO(PackagePublish packagePublish, List<String> names){
        return new PackagePublishVO(packagePublish.getId(),packagePublish.getResId(),packagePublish.getName(),names,packagePublish.getPrice(),packagePublish.getNumber(),packagePublish.getDescription(),packagePublish.getImage(),packagePublish.getDate(),packagePublish.getStartTime(),packagePublish.getEndTime());
    }
}
