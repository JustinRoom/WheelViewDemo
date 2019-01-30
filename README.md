# WheelView
**LatestVersion**

[ ![Download](https://api.bintray.com/packages/justinquote/maven/wheel-view/images/download.svg) ](https://bintray.com/justinquote/maven/wheel-view/_latestVersion)  

<a href='https://bintray.com/justinquote/maven/wheel-view?source=watch' alt='Get automatic notifications about new "wheel-view" versions'><img src='https://www.bintray.com/docs/images/bintray_badge_color.png'></a>

A wheel view library and demo


Scan QRCode to download demo application below:

![](/app/src/main/res/drawable/wheel_view_demo_qr_code.png)

### 1、implementation
+ 1.1、Gradle
```
implementation 'com.android.support:appcompat-v7:XXX'
compile 'jsc.kit.wheel:wheel-view:_latestVersion'
```
+ 1.2、Maven
```
<dependency>
  <groupId>jsc.kit.wheel</groupId>
  <artifactId>wheel-view</artifactId>
  <version>_latestVersion</version>
  <type>pom</type>
</dependency>
```

### 2、attrs
+ 2.1、[WheelView](/wheelLibrary/src/main/java/jsc/kit/wheel/base/WheelView.java)

| 名称 | 类型 | 描述 |
|:---|:---|:---|
|`wheelTextColor`|color|选中item字体颜色|
|`wheelTextSize`|dimension|字体大小|
|`wheelShowCount`|integer|显示item条数，与`wheelItemVerticalSpace`决定了控件的高度|
|`wheelTotalOffsetX`|dimension|X轴方向总弯曲度，决定弧形效果|
|`wheelItemVerticalSpace`|dimension|两个item之间的间距，与`wheelShowCount`决定了控件的高度|
|`wheelRotationX`|float|已X轴为轴心旋转角度，决定3D效果|
|`wheelVelocityUnits`|integer|自动翻滚速度单位|

+ 2.2、[WheelMaskView](/wheelLibrary/src/main/java/jsc/kit/wheel/base/WheelMaskView.java)

| 名称 | 类型 | 描述 |
|:---|:---|:---|
|`wheelMaskLineColor`|color|中间选中item的两条分割线颜色|

+ 2.3、[WheelItemView](/wheelLibrary/src/main/java/jsc/kit/wheel/base/WheelItemView.java)

| 子View | 类型 | 属性 |
|:---|:---|:---|
|`wheelView`|[WheelView](/wheelLibrary/src/main/java/jsc/kit/wheel/base/WheelView.java)|[WheelView](/wheelLibrary/src/main/java/jsc/kit/wheel/base/WheelView.java)所有属性|
|`wheelMaskView`|[WheelMaskView](/wheelLibrary/src/main/java/jsc/kit/wheel/base/WheelMaskView.java)|[WheelMaskView](/wheelLibrary/src/main/java/jsc/kit/wheel/base/WheelMaskView.java)所有属性|

### 3、usage
| 组件 | 使用示例 |
|:---|:---|
|[WheelView](/wheelLibrary/src/main/java/jsc/kit/wheel/base/WheelView.java)|[WheelViewFragment](/app/src/main/java/jsc/exam/com/wheelview/fragments/WheelViewFragment.java)|
|[ColumnWheelDialog](wheelLibrary/src/main/java/jsc/kit/wheel/dialog/ColumnWheelDialog.java)|[ColumnWheelFragment](/app/src/main/java/jsc/exam/com/wheelview/fragments/ColumnWheelFragment.java)|
|[DateTimeWheelDialog](wheelLibrary/src/main/java/jsc/kit/wheel/dialog/DateTimeWheelDialog.java)|[DateTimeWheelFragment](/app/src/main/java/jsc/exam/com/wheelview/fragments/DateTimeWheelFragment.java)|

### 4、Screenshots
+ 4.1、[WheelView](/wheelLibrary/src/main/java/jsc/kit/wheel/base/WheelView.java)

![WheelView](/output/shots/wheel_view.png)

+ 4.2、[ColumnWheelDialog](/wheelLibrary/src/main/java/jsc/kit/wheel/dialog/ColumnWheelDialog.java)

![WheelView](/output/shots/column_wheel01.png)
![WheelView](/output/shots/column_wheel02.png)
![WheelView](/output/shots/column_wheel03.png)
![WheelView](/output/shots/column_wheel04.png)
![WheelView](/output/shots/column_wheel05.png)

+ 4.3、[DateTimeWheelDialog](/wheelLibrary/src/main/java/jsc/kit/wheel/dialog/DateTimeWheelDialog.java)

![OneColumnWheelDialog](/output/shots/date_time_wheel01.png)
![OneColumnWheelDialog](/output/shots/date_time_wheel02.png)
![OneColumnWheelDialog](/output/shots/date_time_wheel03.png)
![OneColumnWheelDialog](/output/shots/date_time_wheel04.png)
![OneColumnWheelDialog](/output/shots/date_time_wheel05.png)

### 5、release log

##### version:0.5.0
+ optimize [WheelMaskView](/wheelLibrary/src/main/java/jsc/kit/wheel/base/WheelMaskView.java):  
+ change `wheelRotationX`(attribution) to float  
+ add `wheelVelocityUnits`(attribution)  

### LICENSE
```
   Copyright 2018 JustinRoom

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
