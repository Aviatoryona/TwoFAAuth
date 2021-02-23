Ext.define('TwoFAuth.view.login.Login', {
    extend: 'Ext.window.Window',

    xtype: 'login-dialog',
    autoShow: true,
    height: 256,
    width: 360,
    layout: {
        type: 'fit'
    },
    iconCls: 'fa fa-key fa-lg',
    title: 'Login',
    closeAction: 'hide',
    closable: false,
    draggable: false,
    resizable: false,

    controller: 'login',

    items: [
        {
            xtype: 'form',
            bodyPadding: 15,
            jsonSubmit:true,
            reference: 'form',
            defaults: {
                xtype: 'textfield',
                anchor: '100%',
                labelWidth: 60
            },
            items: [
                {
                    name: 'username',
                    fieldLabel: 'Username'
                },
                {
                    inputType: 'password',
                    name: 'pwd',
                    fieldLabel: 'Password'
                }
            ],
            dockedItems: [
                {
                    xtype: 'toolbar',
                    dock: 'bottom',
                    items: [
                        {
                            xtype: 'tbfill',
                        },
                        {
                            xtype: 'button',
                            iconCls: 'fa fa-times fa-lg',
                            text: 'Cancel',
                            listeners: {
                                click: 'onButtonClickCancel'
                            }
                        },
                        {
                            xtype: 'button',
                            formBind: true,
                            iconCls: 'fa fa-sign-in fa-lg',
                            text: 'Submit',
                            listeners: {
                                click: 'onButtonClickSubmit'
                            }
                        }
                    ]
                }
            ]
        }
    ]
});