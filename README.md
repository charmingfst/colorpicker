# colorpicker
颜色选择器</br>
![单个颜色选择器](https://github.com/charmingfst/colorpicker/blob/master/images/charge5.gif)
</br>
单个颜色选择器
</br>
![连续多个颜色选择器](https://github.com/charmingfst/colorpicker/blob/master/images/charge4.gif)
</br>
连续多个颜色选择器
</br>
### Useage
Step 1. Add the JitPack repository to your build file
</br>
...
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
...
</br>
Step 2. Add the dependency
</br>
...
	dependencies {
	        compile 'com.github.charmingfst:colorpicker:1.0'
	}
...
</br>
Step 3. layout
</br>
...
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
...
</br>
IntervalColorPicker没有设置默认尺寸，需要在布局文件设定宽高。