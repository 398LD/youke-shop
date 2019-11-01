/**
 * 消息提示组件
 *
 message:' �����ɹ�',    //��ʾ��Ϣ
 time:'2000',           //��ʾʱ�䣨Ĭ�ϣ�2s��
 type:'success',        //��ʾ���ͣ�����4�֣�success.error,info,warning
 showClose:false,       //��ʾ�رհ�ť��Ĭ�ϣ���
 autoClose:true,        //�Ƿ��Զ��رգ�Ĭ�ϣ��ǣ�
 $('.btn-success').on('click',function(){
		$.message('�ɹ�');
	})
 $('.btn-danger').on('click',function(){
		$.message({
			message:'ʧ����ʾ',
			type:'error'
		});
	})
 $('.btn-warning').on('click',function(){
		$.message({
			message:'������ʾ',
			type:'warning'
		});
	})
 $('.btn-info').on('click',function(){
		$.message({
			message:'��Ϣ����',
			type:'info'
		});
	})
 *
 * type:success,error,info,warning
 */

$.extend({
    message: function (options) {
        var defaults = {
            message: ' �ɹ�',
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
        var templateClose = defaults.showClose ? '<a class="c-message--close">×</a>' : '';
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