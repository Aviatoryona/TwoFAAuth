/**
 * The main application class. An instance of this class is created by app.js when it
 * calls Ext.application(). This is the ideal place to handle application launch and
 * initialization details.
 */
Ext.define('TwoFAuth.Application', {
    extend: 'Ext.app.Application',

    name: 'TwoFAuth',

    quickTips: false,
    platformConfig: {
        desktop: {
            quickTips: true
        }
    },

    init: function () {
        var me = this;
        me.splashscreen = Ext.getBody().mask(
            'Loading...', 'splashscreen'
        );
    },

    launch: function () {
        var me = this;
        var task = new Ext.util.DelayedTask(function () {
            //Fade out the body mask
            me.splashscreen.fadeOut({
                duration: 1000,
                remove: true
            });
            //Fade out the icon and message
            me.splashscreen.next().fadeOut({
                duration: 1000,
                remove: true,
                listeners: {
                    afteranimate: function (el, startTime, eOpts) {
                       localStorage.getItem('loggedin')?
                        Ext.create('TwoFAuth.view.main.Main'):
                        Ext.widget('login-dialog');
                    }
                }
            });
        });
        task.delay(2000);
    },

    onAppUpdate: function () {
        Ext.Msg.confirm('Application Update', 'This application has an update, reload?',
            function (choice) {
                if (choice === 'yes') {
                    window.location.reload();
                }
            }
        );
    }
});
