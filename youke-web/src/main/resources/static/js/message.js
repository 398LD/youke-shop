/**
 * 娑堟伅鎻愮ず缁勪欢
 *
 message:' 操作成功',    //提示信息
 time:'2000',           //显示时间（默认：2s）
 type:'success',        //显示类型，包括4种：success.error,info,warning
 showClose:false,       //显示关闭按钮（默认：否）
 autoClose:true,        //是否自动关闭（默认：是）
 $('.btn-success').on('click',function(){
		$.message('成功');
	})
 $('.btn-danger').on('click',function(){
		$.message({
			message:'失败提示',
			type:'error'
		});
	})
 $('.btn-warning').on('click',function(){
		$.message({
			message:'警告提示',
			type:'warning'
		});
	})
 $('.btn-info').on('click',function(){
		$.message({
			message:'信息提醒',
			type:'info'
		});
	})
 *
 * type:success,error,info,warning
 */

$.extend({
    message: function (options) {
        var defaults = {
            message: ' 成功',
            time: '2000',
            type: 'success',
            showClose: false,
            autoClose: true,
            onClose: function () {
            }
        };

        if (typeof options === 'string') {
            defaults.message = options;
        }
        if (typeof options === 'object') {
            defaults = $.extend({}, defaults, options);
        }
        var templateClose = defaults.showClose ? '<a class="c-message--close">脳</a>' : '';
        var template = '<div class="c-message messageFadeInDown">' +
            '<i class=" c-message--icon c-message--' + defaults.type + '"></i>' +
            templateClose +
            '<div class="c-message--tip">' + defaults.message + '</div>' +
            '</div>';
        var _this = this;
        var $body = $('body');
        var $message = $(template);
        var timer;
        var closeFn, removeFn;
        closeFn = function () {
            $message.addClass('messageFadeOutUp');
            $message.one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function () {
                removeFn();
            })
        };
        removeFn = function () {
            $message.remove();
            defaults.onClose(defaults);
            clearTimeout(timer);
        };
        $('.c-message').remove();
        $body.append($message);
        $message.css({
            'margin-left': '-' + $message.width() / 2 + 'px'
        })
        $message.one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function () {
            $message.removeClass('messageFadeInDown');
        });
        $body.on('click', '.c-message--close', function (e) {
            closeFn();
        });
        if (defaults.autoClose) {
            timer = setTimeout(function () {
                closeFn();
            }, defaults.time)
        }
    }
});