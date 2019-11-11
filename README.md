# Parallax

[![](https://jitpack.io/v/profugocanis/parallax.svg)](https://jitpack.io/#profugocanis/parallaxview)

Add it in your root build.gradle at the end of repositories:

```groovy
repositories {
  ...
  maven { url 'https://jitpack.io' }
}

dependencies {
  ...
  implementation "com.github.profugocanis:parallaxview:$version"
}
```
# Use in Activity

```kotlin

override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        ParallaxView.Builder(this)
                   .setContentView(R.layout.activity_main)
                   .setRecyclerView(recyclerView)
                   .setToolBarView(toolBarView)
                   .setBottomView(bottomView)
                   .onBlur()
                   .build()
        ...                   
}
      

```

# Use in Fragment

```kotlin
override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_blank, container, false)
        ...
        return ParallaxView.Builder(activity)
            .setContentView(view)
            .setToolBarView(view.toolBarView)
            .setBottomView(view.bottomView)
            .onBlur()
            .build()
 }

```
