/*
 * This file launches the application by asking Ext JS to create
 * and launch() the Application class.
 */
Ext.application({
    extend: 'TwoFAuth.Application',

    name: 'TwoFAuth',

    requires: [
        // This will automatically load all classes in the TwoFAuth namespace
        // so that application classes do not need to require each other.
        'TwoFAuth.*'
    ],

    // The name of the initial view to create.
    // mainView: 'TwoFAuth.view.login.Login'
});
