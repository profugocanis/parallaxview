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
ParallaxView.Builder()
            .setContentView(R.layout.activity_main, this)
//            .setRecyclerView(recyclerView)
            .setToolBarView(toolbar)
            .setBottomView(linearLayoutBottom)

```

# Use in Fragment

```kotlin
override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return ParallaxView.Builder()
            .setContentView(R.layout.fragment_blank, activity)
            .buildFragment(activity)
 }

```
