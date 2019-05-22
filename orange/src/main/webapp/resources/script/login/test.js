$(document).ready(function () {
/*    $("#panel h5.head").bind("click",function () {
        var $content = $(this).next("div.content");
        if($content.is(":visible")) {
            $content.hide();
        } else {
            $content.show();
        }
    })*/
/*    $("#panel h5.head").mouseover(function () {
        $(this).next("div.content").show();
    })
    $("#panel h5.head").mouseout(function () {
        $(this).next("div.content").hide();
    })*/
    $("#panel h5.head").hover(function () {
            $(this).next("div.content").show();
        }, function () {
            $(this).next("div.content").hide();
        })
})