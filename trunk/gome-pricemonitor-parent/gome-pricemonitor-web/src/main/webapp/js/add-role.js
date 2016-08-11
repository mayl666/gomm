/**
 * Created by hanpan on 2015/10/29.
 */
var setting = {
    check: {
        enable: true
    },
    data: {
        simpleData: {
            enable: true
        }
    }
};
//var zTreeNodes = [{
//    checked : true,
//    ......
//}];
var zNodes =[
    { id:1, pId:0, name:"后台管理平台", open:true},
    { id:11, pId:1, name:"用户管理", open:true},
    { id:111, pId:11, name:"用户管理列表"},
    { id:12, pId:1, name:"设备管理", open:true},
    { id:121, pId:12, name:"设备类型列表"},
    { id:122, pId:12, name:"设备品牌列表"},
    { id:123, pId:12, name:"设备列表"},
    { id:13, pId:1, name:"商品管理", open:true},
    { id:131, pId:13, name:"商品类别列表"},
    { id:132, pId:13, name:"商品列表"},
    { id:14, pId:1, name:"广告管理", open:true},
    { id:141, pId:14, name:"频道列表"},
    { id:142, pId:14, name:"广告列表"},
    //{ id:15, pId:1, name:"主题管理（待定）", open:false},
    { id:16, pId:1, name:"消息管理", open:true},
    { id:161, pId:16, name:"意见列表"},
    { id:17, pId:1, name:"数据统计", open:true},
    { id:171, pId:17, name:"用户统计"},
    { id:172, pId:17, name:"设备统计"},
    { id:173, pId:17, name:"商品统计"},
    //{ id:174, pId:17, name:"主题统计（待定）"},
    { id:18, pId:1, name:"权限管理", open:true},
    { id:181, pId:18, name:"账号列表"},
    { id:182, pId:18, name:"角色列表"},
    { id:19, pId:1, name:"操作日志", open:true},
    { id:191, pId:19, name:"日志列表"},

];

var code;

function setCheck() {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo")
    //    py = $("#py").attr("checked")? "p":"",
    //    sy = $("#sy").attr("checked")? "s":"",
    //    pn = $("#pn").attr("checked")? "p":"",
    //    sn = $("#sn").attr("checked")? "s":"",
    //    type = { "Y":py + sy, "N":pn + sn};
    //zTree.setting.check.chkboxType = type;
    ////setting.check.chkboxType = { "Y" : "s", "N" : "s" };
    //showCode('setting.check.chkboxType = { "Y" : "' + type.Y + '", "N" : "' + type.N + '" };');
}
function showCode(str) {
    if (!code) code = $("#code");
    code.empty();
    code.append("<li>"+str+"</li>");
}
//全选
function CheckAllNodes() {
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    treeObj.checkAllNodes(true);
}
$(document).ready(function(){
    $.fn.zTree.init($("#treeDemo"), setting, zNodes);
    //$("#treeDemo_1_check").click(function(){
    //
    //    $(this).attr("checked");
    //});
    setCheck();
    CheckAllNodes();
    alert('3333');
//    $.formValidator.initConfig({theme:"baidu",mode:"AutoTip",submitOnce:true,formID:"roleForm",
//        submitAfterAjaxPrompt : '有数据正在异步验证，请稍等...'
//    });
//    $("#roleName").formValidator({onEmpty:"请输入角色名称", onFocus:"请输入4~16位字符的帐号",tipCss:{height:36,width:460}}).inputValidator({min:4,max:16,onError:"你输入的角色名称长度不正确,请确认"});
//    $("#roledic").formValidator({onEmpty:"请输入角色描述", onFocus:"请输入角色描述，不超过200字符",tipCss:{height:36,width:460}}).inputValidator({min:4,max:200,onError:"你输入的角色描述长度长度不对，请确认"});
//    $.formValidator.reloadAutoTip();
});