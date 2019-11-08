# Parallax

[![](https://jitpack.io/v/profugocanis/parallax.svg)](https://jitpack.io/#profugocanis/parallax)

Add it in your root build.gradle at the end of repositories:

```groovy
repositories {
  ...
  maven { url 'https://jitpack.io' }
}

dependencies {
  ...
  implementation "com.github.profugocanis:parallax:$version"
}
```
# Use activity

```kotlin
ParallaxView.Builder()
            .setContentView(R.layout.activity_main, this)
            .build(this)
            .setToolBarView(toolbar)
            .setBottomView(linearLayoutBottom)

```
