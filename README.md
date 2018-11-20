# WheelView
**LatestVersion**

[ ![Download](https://api.bintray.com/packages/justinquote/maven/wheel-view/images/download.svg) ](https://bintray.com/justinquote/maven/wheel-view/_latestVersion)

A wheel view library and demo


Scan QRCode to download demo application below:

![](/output/wheel_view_demo_qr_code.png)

### implementation
```
allprojects {
    repositories {
        ...
        maven { url "https://dl.bintray.com/justinquote/maven" }
    }
}
```

+ Gradle
```
compile 'jsc.kit.wheel:wheel-view:_latestVersion'
```
+ Maven
```
<dependency>
  <groupId>jsc.kit.wheel</groupId>
  <artifactId>wheel-view</artifactId>
  <version>0.1.1</version>
  <type>pom</type>
</dependency>
```

### Screenshots
+ [WheelView](/wheelLibrary/src/main/java/jsc/kit/wheel/base/WheelView.jave)

![WheelView](/output/shots/wheel_view.png)

+ [ColumnWheelDialog](/wheelLibrary/src/main/java/jsc/kit/wheel/dialog/ColumnWheelDialog.jave)

![WheelView](/output/shots/column_wheel01.png)
![WheelView](/output/shots/column_wheel02.png)
![WheelView](/output/shots/column_wheel03.png)
![WheelView](/output/shots/column_wheel04.png)
![WheelView](/output/shots/column_wheel05.png)

+ [DateTimeWheelDialog](/wheelLibrary/src/main/java/jsc/kit/wheel/dialog/DateTimeWheelDialog.jave)

![OneColumnWheelDialog](/output/shots/date_time_wheel01.png)
![OneColumnWheelDialog](/output/shots/date_time_wheel02.png)
![OneColumnWheelDialog](/output/shots/date_time_wheel03.png)
![OneColumnWheelDialog](/output/shots/date_time_wheel04.png)
![OneColumnWheelDialog](/output/shots/date_time_wheel05.png)
