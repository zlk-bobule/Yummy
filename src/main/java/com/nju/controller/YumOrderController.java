package com.nju.controller;

import com.nju.enums.OrderState;
import com.nju.enums.ResultMessage;
import com.nju.model.PackagePublish;
import com.nju.model.ProductPublish;
import com.nju.model.YumOrder;
import com.nju.service.PublishService;
import com.nju.service.YumOrderService;
import com.nju.vo.PackagePublishVO;
import com.nju.vo.ProductPublishVO;
import com.nju.vo.YumOrderVO;
import com.nju.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.dom.DOMCryptoContext;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/order")
public class YumOrderController {

    @Autowired
    private PublishService publishService;

    @Autowired
    private YumOrderService yumOrderService;

    /**
     * 下订单
     * @param yumOrderVO
     * @return
     */
    @PostMapping(value = "/place")
    public ResultVO placeOrder(@RequestBody YumOrderVO yumOrderVO){
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<Long> proIds = new ArrayList<>();
        for(String name : yumOrderVO.getProductNames()){
            Long id = publishService.findProductIdByProductNameAndResId(name,yumOrderVO.getResId(),formatter.format(currentTime),1);
            if(id == -1L){
                for(Long proId:proIds){
                    publishService.addProdutNumber(proId);
                }
                return new ResultVO(ResultMessage.FAILED.getCode(),ResultMessage.FAILED.getValue(),null);
            }
            proIds.add(id);
        }
        List<Long> pacIds = new ArrayList<>();
        for(String name : yumOrderVO.getPackageNames()){
            Long id = publishService.findPackageIdByPacNameAndResIdAndDate(name,yumOrderVO.getResId(),formatter.format(currentTime),1);
            if(id == -1L){
                for (Long pacId:pacIds){
                    publishService.addPackageNumber(pacId);
                }
                return new ResultVO(ResultMessage.FAILED.getCode(),ResultMessage.FAILED.getValue(),null);
            }
            pacIds.add(id);
        }
        YumOrder yumOrder = new YumOrder(yumOrderVO.getUserId(), yumOrderVO.getResId(), proIds, pacIds, yumOrderVO.getPrice(), yumOrderVO.getDeliverFee(), yumOrderVO.getSum(), currentTime, OrderState.PLACE, yumOrderVO.getUserAddressId(),yumOrderVO.getRemarks(),yumOrderVO.getEndTime());
//        YumOrder yumOrder = new YumOrder(yumOrderVO.getUserId(), yumOrderVO.getResId(), proIds, pacIds, yumOrderVO.getPrice(), yumOrderVO.getDeliverFee(), yumOrderVO.getSum(), "","");
        List<Object> list = yumOrderService.placeOrder(yumOrder);
        ResultMessage resultMessage = (ResultMessage) list.get(0);
        if(resultMessage == ResultMessage.SUCCESS){
            Long id = (Long)list.get(1);
            return new ResultVO(resultMessage.getCode(),resultMessage.getValue(),id);
        }else{
            return new ResultVO(resultMessage.getCode(),resultMessage.getValue(),null);
        }

    }

    /**
     * 取消订单
     * @param orderId
     * @return
     */
    @GetMapping(value = "/cancelOrder/{orderId}")
    public ResultVO modifyOrder(@PathVariable("orderId") Long orderId){
        Double res = yumOrderService.cancelOrder(orderId);
        return new ResultVO(ResultMessage.SUCCESS.getCode(),ResultMessage.SUCCESS.getValue(),res);
    }

    /**
     * 确认订单到达
     * @param orderId
     * @return
     */
    @GetMapping(value = "/confirmArrive/{orderId}")
    public ResultVO orderArrived(@PathVariable("orderId") Long orderId){
        ResultMessage resultMessage = yumOrderService.orderArrived(orderId);
        return new ResultVO(resultMessage.getCode(),resultMessage.getValue(),null);
    }

    /**
     * 支付订单
     * @param userId
     * @param sum
     * @return
     */
    @GetMapping(value = "/pay")
    public ResultVO pay(@RequestParam("userId") Long userId, @RequestParam("sum") Double sum, @RequestParam("id") Long id){
        ResultMessage resultMessage = yumOrderService.pay(userId, sum, id);
        return new ResultVO(resultMessage.getCode(),resultMessage.getValue(),null);
    }

    /**
     * 通过状态得到
     * @param state
     * @return
     */
    @GetMapping(value = "/getOrderList")
    public ResultVO getOrderListByState(@RequestParam("state") String state, @RequestParam("sortBy") String sortBy, @RequestParam("userId") Long userId){
        List<YumOrder> yumOrders = yumOrderService.getOrderList(state, sortBy, userId);
        List<YumOrderVO> yumOrderVOS = getYumOrderVOs(yumOrders);
        return new ResultVO(ResultMessage.SUCCESS.getCode(),ResultMessage.SUCCESS.getValue(),yumOrderVOS);
    }

    /**
     * 得到送餐记录详情
     * @param resId
     * @return
     */
    @GetMapping(value = "/getDeliverList/{resId}")
    public ResultVO getDeliverList(@PathVariable("resId") Long resId){
        List<YumOrder> yumOrders = yumOrderService.getDeliverList(resId);
        List<YumOrderVO> yumOrderVOS = getYumOrderVOs(yumOrders);
        return new ResultVO(ResultMessage.SUCCESS.getCode(),ResultMessage.SUCCESS.getValue(),yumOrderVOS);
    }

    /**
     * 得到餐厅退订记录
     * @param resId
     * @return
     */
    @GetMapping(value = "/getCancelList/{resId}")
    public ResultVO getCancelList(@PathVariable("resId") Long resId){
        List<YumOrder> yumOrders = yumOrderService.getCancelList(resId);
        List<YumOrderVO> yumOrderVOS = getYumOrderVOs(yumOrders);
        return new ResultVO(ResultMessage.SUCCESS.getCode(),ResultMessage.SUCCESS.getValue(),yumOrderVOS);
    }

    /**
     * 得到餐厅财务状况
     * @param resId
     * @return
     */
    @GetMapping(value = "/getFinance/{resId}")
    public ResultVO getFinance(@PathVariable("resId") Long resId){
        Map<String,Double> map = yumOrderService.getFinance(resId);
        List list = new ArrayList<>();
        for(String date : map.keySet()){
            List<Object> list1 = new ArrayList<>();
            list1.add(date);
            list1.add(map.get(date));
            list.add(list1);
        }
        return new ResultVO(ResultMessage.SUCCESS.getCode(),ResultMessage.SUCCESS.getValue(),list);
    }

    /**
     * 通过订单可能出现的单品名或套餐名找到整个单品或套餐
     * @param name
     * @param resId
     * @return
     */
    @GetMapping(value = "/getProOrPacImage")
    public ResultVO getProImageOrPacImage(@RequestParam("name") String name, @RequestParam("resId") Long resId, @RequestParam("placeTime") String placeTime) {
        String[] strings = placeTime.split(" ");
        Long proId = publishService.findProductIdByProductNameAndResId(name, resId, strings[0], 0);
        if (proId != null) {
            ProductPublish productPublish = publishService.findProductByid(proId);
            ProductPublishVO productPublishVO = new ProductPublishVO(productPublish.getId(), productPublish.getResId(), productPublish.getName(), productPublish.getPrice(), productPublish.getNumber(), productPublish.getDescription(), productPublish.getImage(), productPublish.getDate(), productPublish.getStartTime(), productPublish.getEndTime());
            return new ResultVO(ResultMessage.SUCCESS.getCode(), ResultMessage.SUCCESS.getValue(), productPublishVO);
        } else {
            Long pacId = publishService.findPackageIdByPacNameAndResIdAndDate(name, resId, strings[0],0);
            PackagePublish packagePublish = publishService.findPackageById(pacId);
            List<String> list = new ArrayList<>();
            for (Long productId : packagePublish.getProductIds()) {
                list.add(publishService.getProNameByProId(productId));
            }
            PackagePublishVO packagePublishVO = new PackagePublishVO(packagePublish.getId(), packagePublish.getResId(), packagePublish.getName(), list, packagePublish.getPrice(), packagePublish.getNumber(), packagePublish.getDescription(), packagePublish.getImage(), packagePublish.getDate(), packagePublish.getStartTime(), packagePublish.getEndTime());
            return new ResultVO(ResultMessage.SUCCESS.getCode(), ResultMessage.SUCCESS.getValue(), packagePublishVO);
        }
    }

    /**
     * 判断用户是否为新用户
     * @param userId
     * @param resId
     * @return
     */
    @GetMapping(value = "/judgePlace")
    public ResultVO judgePlace(@RequestParam("userId") Long userId, @RequestParam("resId") Long resId){
        ResultMessage resultMessage = yumOrderService.judgePlace(userId, resId);
        return new ResultVO(resultMessage.getCode(),resultMessage.getValue(),null);
    }

    @GetMapping(value = "/getOrderInfo/{orderId}")
    public ResultVO getOrderInfo(@PathVariable("orderId") Long orderId){
        YumOrder yumOrder = yumOrderService.getOrderInfo(orderId);
        YumOrderVO yumOrderVO = getYumOrderVO(yumOrder);
        return new ResultVO(ResultMessage.SUCCESS.getCode(),ResultMessage.SUCCESS.getValue(),yumOrderVO);
    }

    /**
     * List<YumOrder>->List<YumOrderList>
     * @param yumOrders
     * @return
     */
    private List<YumOrderVO> getYumOrderVOs(List<YumOrder> yumOrders){
        List<YumOrderVO> yumOrderVOS = new ArrayList<>();
        for(YumOrder yumOrder : yumOrders){
            yumOrderVOS.add(getYumOrderVO(yumOrder));
        }
        return yumOrderVOS;
    }

    private YumOrderVO getYumOrderVO(YumOrder yumOrder){
        List<String> productNames = new ArrayList<>();
        for(Long proId:yumOrder.getProductIds()){
            productNames.add(publishService.getProNameByProId(proId));
        }
        List<String> packageNames = new ArrayList<>();
        for(Long pacId:yumOrder.getPackageIds()){
            packageNames.add(publishService.getPacNameByPacId(pacId));
        }
        YumOrderVO yumOrderVO = new YumOrderVO(yumOrder.getId(),yumOrder.getUserId(),yumOrder.getResId(),productNames,packageNames,yumOrder.getPrice(),yumOrder.getDeliverFee(),yumOrder.getSum(),yumOrder.getPlaceTime(),yumOrder.getOrderState(),yumOrder.getUserAddressId(),yumOrder.getRemarks(),yumOrder.getEndTime());
        return yumOrderVO;
    }
}
