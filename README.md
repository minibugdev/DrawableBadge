# Drawable Badge
[![Release](https://jitpack.io/v/minibugdev/DrawableBadge.svg)](https://jitpack.io/#minibugdev/DrawableBadge)
[![GitHub license](https://img.shields.io/badge/license-MIT-blue.svg)](https://raw.githubusercontent.com/minibugdev/DrawableBadge/master/LICENSE)

Drawable Badge is a android library for adding badges to drawables.

![Drawable Badge Android Library](https://github.com/minibugdev/DrawableBadge/blob/master/screenshot.png?raw=true)

## Usage
Generate drawable with badge
``` kotlin
val drawable = DrawableBadge.Builder(context)
    .drawableResId(R.mipmap.ic_launcher_round)
    .badgeColor(R.color.badgeColor)
    .badgeSize(R.dimen.badge_size)
    .badgePosition(BadgePosition.TOP_RIGHT)
    .textColor(R.color.textColor)
    .build()
    .get(99)
```
Apply to image view.
``` kotlin
imageViewBadge.setImageDrawable(drawable) 
```

## Customize
- `drawableResId`: Drawable resource id to added badges.
- `drawable`: Drawable to added badges.
- `bitmap`: Bitmap  to added badges.
- `textColor`: Badge text color resource id, default `#FFFFFF`.
- `badgeColor`: Badge color resource id , default `#FF0000`.
- `badgeSize`: Badge size dimension id, default `16dp`.
- `badgePosition`: Position of Badge which need to added. (`TOP_LEFT`, `TOP_RIGHT`, `BOTTOM_RIGHT`, `BOTTOM_LEFT`), default `TOP_RIGHT`.

## Download
``` groovy
repositories {
    maven { url 'https://jitpack.io' }
}
```

``` groovy
compile 'com.github.minibugdev:drawablebadge:1.0.0'
```


## License
```
MIT License

Copyright (c) 2017 Teeranai.P

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE. 
```