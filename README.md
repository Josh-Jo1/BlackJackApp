# Black Jack App

DEVELOPMENT IN PROGRESS

Initially, I had Hand in GameViewModel as LiveData, which was then observed in PlayFragment and notified the recycler adapter
whenever the data set changed. However, I later changed to the current approach of simply having Hand as an object and
notifying the recycler adapter more efficiently when the dataset changed (by providing the specific index).

## Credits

[Tiled Background Image](https://static.vecteezy.com/system/resources/previews/002/582/114/non_2x/modern-abstract-casino-background-with-shiny-blue-playing-cards-signs-poker-symbols-on-black-background-casino-symbols-widescreen-wallpaper-vector.jpg)\
[Table Image](https://www.nicepng.com/ourpic/u2w7q8o0a9a9u2q8_poker-table-png-poker-table-online/)

[Card Images](https://code.google.com/archive/p/vector-playing-cards/downloads)

[Card Flip Animation](https://medium.com/geekculture/how-to-add-card-flip-animation-in-the-android-app-3060afeadd45)

[Play Icon](https://www.flaticon.com/free-icon/play-button-arrowhead_27223)
[Refresh Icon](https://www.flaticon.com/free-icon/refresh-buttons_16498)
