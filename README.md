# AndroKeePass (under development)

KeePass Android App supporting **KeePass 2.x database files**.

Security
--------
There are some security checks:
- Emulator: checks that app is not running in an emulator
- Checks app certificate
- Checks that app is not running in a debug build
- Logs are disabled in a release build
- Adb screen capture is disabled in release build
- Anti hijaking is applied to buttons
- ProGuard obfuscation
- Configurable pass code lock

Many of this checks are not useful if app is installed from other source 
than **Google Play Store** or **FDroid**. An apk from other source could be
compiled without any of this checks or contain malicious code.

Libraries used on this project
------------------------------------
* [AppCompat, CardView, RecyclerView and DesignLibrary][1]
* [RxJava][2]
* [Dagger 2][3]
* [Junit][4]
* [Mockito][5]
* [Espresso][6]
* [Android Simple Storage][7]
* [openkeepass][8]
* [Butter Knife][9]
* [Timber][10]
* [Parceler][11]
* [Dexter][12]
* [PiracyChecker][13]
* [PasscodeLock-Android][14]


[1]: http://developer.android.com/intl/es/tools/support-library/index.html
[2]: https://github.com/ReactiveX/RxAndroid
[3]: https://github.com/google/dagger
[4]: http://developer.android.com/intl/es/reference/junit/framework/package-summary.html
[5]: http://mockito.org/
[6]: https://google.github.io/android-testing-support-library/docs/espresso/
[7]: https://github.com/sromku/android-simple-storage
[8]: https://github.com/cternes/openkeepass
[9]: https://github.com/JakeWharton/butterknife
[10]: https://github.com/JakeWharton/timber
[11]: https://github.com/johncarl81/parceler
[12]: https://github.com/Karumi/Dexter
[13]: https://github.com/javiersantos/PiracyChecker
[14]: https://github.com/wordpress-mobile/PasscodeLock-Android

License
-------

        DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE 
                    Version 2, December 2004

        Copyright (C) 2004 Sam Hocevar <sam@hocevar.net>
        
        Everyone is permitted to copy and distribute verbatim or modified 
        copies of this license document, and changing it is allowed as long 
        as the name is changed. 

        DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE         
        TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
        
        0. You just DO WHAT THE FUCK YOU WANT TO.
    




