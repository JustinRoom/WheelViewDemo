# WheelView

### 1、attrs
+ 1.1、[WheelView](/wheelLibrary/src/main/java/jsc/kit/wheel/base/WheelView.java)

| 名称 | 类型 | 描述 |
|:---|:---|:---|
|`wheelTextColor`|color|选中item字体颜色|
|`wheelTextSize`|dimension|字体大小|
|`wheelShowCount`|integer|显示item条数，与`wheelItemVerticalSpace`决定了控件的高度|
|`wheelTotalOffsetX`|dimension|X轴方向总弯曲度，决定弧形效果|
|`wheelItemVerticalSpace`|dimension|两个item之间的间距，与`wheelShowCount`决定了控件的高度|
|`wheelRotationX`|float|已X轴为轴心旋转角度，决定3D效果|
|`wheelVelocityUnits`|integer|自动翻滚速度单位|

+ 1.2、[WheelMaskView](/wheelLibrary/src/main/java/jsc/kit/wheel/base/WheelMaskView.java)

| 名称 | 类型 | 描述 |
|:---|:---|:---|
|`wheelMaskLineColor`|color|中间选中item的两条分割线颜色|

+ 1.3、[WheelItemView](/wheelLibrary/src/main/java/jsc/kit/wheel/base/WheelItemView.java)

| 子View | 类型 | 属性 |
|:---|:---|:---|
|`wheelView`|[WheelView](/wheelLibrary/src/main/java/jsc/kit/wheel/base/WheelView.java)|[WheelView](/wheelLibrary/src/main/java/jsc/kit/wheel/base/WheelView.java)所有属性|
|`wheelMaskView`|[WheelMaskView](/wheelLibrary/src/main/java/jsc/kit/wheel/base/WheelMaskView.java)|[WheelMaskView](/wheelLibrary/src/main/java/jsc/kit/wheel/base/WheelMaskView.java)所有属性|

### 2、usage
| 组件 | 使用示例 |
|:---|:---|
|[WheelView](/wheelLibrary/src/main/java/jsc/kit/wheel/base/WheelView.java)|[WheelViewFragment](/app/src/main/java/jsc/exam/com/wheelview/fragments/WheelViewFragment.java)|
|[ColumnWheelDialog](wheelLibrary/src/main/java/jsc/kit/wheel/dialog/ColumnWheelDialog.java)|[ColumnWheelFragment](/app/src/main/java/jsc/exam/com/wheelview/fragments/ColumnWheelFragment.java)|
|[DateTimeWheelDialog](wheelLibrary/src/main/java/jsc/kit/wheel/dialog/DateTimeWheelDialog.java)|[DateTimeWheelFragment](/app/src/main/java/jsc/exam/com/wheelview/fragments/DateTimeWheelFragment.java)|

### 3、Screenshots
+ 3.1、[WheelView](/wheelLibrary/src/main/java/jsc/kit/wheel/base/WheelView.java)

![WheelView](/output/shots/wheel_view.png)

+ 3.2、[ColumnWheelDialog](/wheelLibrary/src/main/java/jsc/kit/wheel/dialog/ColumnWheelDialog.java)

![WheelView](/output/shots/column_wheel01.png)
![WheelView](/output/shots/column_wheel02.png)
![WheelView](/output/shots/column_wheel03.png)
![WheelView](/output/shots/column_wheel04.png)
![WheelView](/output/shots/column_wheel05.png)

+ 3.3、[DateTimeWheelDialog](/wheelLibrary/src/main/java/jsc/kit/wheel/dialog/DateTimeWheelDialog.java)

![OneColumnWheelDialog](/output/shots/date_time_wheel01.png)
![OneColumnWheelDialog](/output/shots/date_time_wheel02.png)
![OneColumnWheelDialog](/output/shots/date_time_wheel03.png)
![OneColumnWheelDialog](/output/shots/date_time_wheel04.png)
![OneColumnWheelDialog](/output/shots/date_time_wheel05.png)
