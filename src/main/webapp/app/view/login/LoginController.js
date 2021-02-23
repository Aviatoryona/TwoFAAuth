Ext.define('TwoFAuth.view.login.LoginController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.login',

    onTextFieldSpecialKey: function (field, e, options) { },

    onTextFieldKeyPress: function (field, e, options) { },

    onButtonClickCancel: function (button, e, options) {
        this.lookupReference('form').reset();
    },

    onButtonClickSubmit: function (button, e, options) {
        var me = this;
        if (me.lookupReference('form').isValid()) {
            me.doLogin();
        }
    },

    doLogin: function () {
        this.getView().mask('Please wait...');
        var me = this,
            form = me.lookupReference('form');
        let formValues = form.getValues();
        form.submit({
            clientValidation: true,
            url: `api/users/login/${formValues.username}/${formValues.pwd}`,
            scope: me,
            success: 'onLoginSuccess',
            failure: 'onLoginFailure'
        });
    },

    onLoginFailure: function (form, action) {
        this.getView().unmask();
        Ext.Msg.show({
            title: 'Failed!',
            msg: 'Incorrect credentials',
            icon: Ext.Msg.ERROR,
            buttons: Ext.Msg.OK
        });
    },

    onLoginSuccess: function (form, action) {
        var me = this;
        me.getView().unmask();

        var result = Ext.JSON.decode(action.response.responseText, true);
        console.log(result);
        if (result.data.isTwoFactorEnabled) {
            me.getView().mask("2FA enabled, check your mobile device...");
            console.log("Initiate 2FA");
            me.initiateTwoFA(result);
        } else {
            this.getView().close();
            localStorage.setItem('loggedin', true);
            // Ext.widget('app-main');
            window.location.reload();
        }
    },

    initiateTwoFA: function (res) {
        var me = this;
        Ext.Ajax.request({
            url: `api/users/iniate2FA/${res.data.id}/${res.data.oneSignalToken}`,
            method: 'POST',
            success: function (response, opts) {
                me.watch(res.data.id, 0);
            },

            failure: function (response, opts) {
                console.log('server-side failure with status code ' + response.status);
                Ext.Msg.show({
                    title: 'Sorry!',
                    msg: 'Failed to send request to device',
                    icon: Ext.Msg.INFO,
                    buttons: Ext.Msg.OK
                });
            },
        });
    },

    watch: function (id, count) {
        var me = this;
        console.log("watch...." + count);
        setTimeout(function(){
            Ext.Ajax.request({
                url: `api/users/requestStatus/${id}`,
                method: 'GET',
                success: function (response, opts) {
                    var obj = Ext.decode(response.responseText);
                    //waiting user action
                    if (!obj.data[1]) {
                        if (count > 4) {
                            me.getView().unmask();
                            Ext.Msg.show({
                                title: 'Sorry!',
                                msg: 'No response',
                                icon: Ext.Msg.ERROR,
                                buttons: Ext.Msg.OK
                            });
                            return;
                        };
                        me.watch(id, count + 1);
                        return;
                    }
                    //User did action
                    if (obj.data[1]) {
                        localStorage.setItem('loggedin', true);
                        window.location.reload();
                    } else {
                        me.getView().unmask();
                        Ext.Msg.show({
                            title: 'Sorry!',
                            msg: 'Request Denied',
                            icon: Ext.Msg.INFO,
                            buttons: Ext.Msg.OK
                        });
                    }
                },
    
                failure: function (response, opts) {
                    if (count > 4) {
                        me.getView().unmask();
                        Ext.Msg.show({
                            title: 'Sorry!',
                            msg: 'No response',
                            icon: Ext.Msg.ERROR,
                            buttons: Ext.Msg.OK
                        });
                        return;
                    };
                    me.watch(id, count + 1);
                }
            });
        },5000);
    }
});