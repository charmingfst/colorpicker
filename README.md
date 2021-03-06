colorpicker
===

颜色选择器

![单个颜色选择器](https://github.com/charmingfst/colorpicker/blob/master/images/charge5.gif)

单个颜色选择器

![连续多个颜色选择器](https://github.com/charmingfst/colorpicker/blob/master/images/charge4.gif)

连续多个颜色选择器,并可以反向


Usage
====================

Step 1. Add the JitPack repository to your build file
```Groovy
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Step 2. Add the dependency
```Groovy
	dependencies {
	        compile 'com.github.charmingfst:colorpicker:1.0'
	}
```
Step 3. layout
```Xml
<com.chm.circle.CircleColorPicker
        android:id="@+id/color_picker"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:text="颜色"
        app:text_size="50sp"
        app:thumb="@drawable/white_ball">
</com.chm.circle.CircleColorPicker>

<com.chm.circle.IntervalColorPicker
        android:layout_width="260dp"
        android:layout_height="260dp"
        app:thumbHigh="@drawable/color_picker_high"
        app:thumbLow="@drawable/color_picker_low"
        app:interval="14"
        app:ring_breadth="10dp">
</com.chm.circle.IntervalColorPicker>
```
Note:
- IntervalColorPicker没有设置默认尺寸，需要在布局文件设定宽高。
- interval表示选择几种连续的颜色，ring_breadth表示圆环宽度。

Step 4. code
```Java
override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        color_picker.setOnColorChangeListener { color.setBackgroundColor(it) }
    }
```
说明：这里使用了kotlin,代码比java简化了不少。
```Java
public void setColors(String... colors)
```
自定义颜色种类

	
