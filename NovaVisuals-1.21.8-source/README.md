# NovaVisuals — Minecraft Fabric 1.21.8

**Projekt źródłowy, a nie gotowy JAR.** Nie został skompilowany ani przetestowany w grze w tym środowisku.

## Funkcje
- Menu **Right Shift**, włączanie/wyłączanie HUD.
- FPS, WASD/Space, armor, efekty, XYZ.
- Przeciąganie pozycji HUD w edytorze, kolory, skala, przezroczystość.
- Profile ustawień grafiki: LOW, BALANCED, MAX FPS (nazwy profili; LOW jest najlżejszy).
- Spotify: odczyt aktualnego utworu i wykonawcy przez oficjalne Spotify Web API.
- Automatyczny zapis konfiguracji w `config/novavisuals.json`.

## Kompilacja na Windows
1. Zainstaluj **JDK 21** i **Gradle 8.14+** (lub dodaj Gradle Wrapper).
2. Otwórz terminal w katalogu projektu i uruchom `gradle build`.
3. Po udanej kompilacji skopiuj plik z `build/libs/` (bez `sources`) do `.minecraft/mods`.
4. Potrzebujesz **Fabric Loader 1.21.8** oraz **Fabric API**.

## Spotify — ważne
Widget jest odczytem przez Spotify Web API. **Nie ma jeszcze logowania OAuth w menu**, okładek ani sterowania odtwarzaniem. Aby uruchomić odczyt, potrzebny jest token OAuth z zakresem `user-read-currently-playing` uzyskany przez autoryzację Spotify. Wpisz go do `spotifyAccessToken` w pliku konfiguracyjnym po pierwszym uruchomieniu. Token wygasa; automatyczne odświeżanie nie jest jeszcze zaimplementowane. **Nie udostępniaj nikomu pliku konfiguracyjnego z tokenem.**

## Ograniczenia
- Mod nie jest samodzielnym zamiennikiem Sodium; możesz używać Sodium osobno.
- Presety zmieniają dystans renderowania, symulację i cząsteczki, ale nie gwarantują wzrostu FPS.
- Przed użyciem na głównym profilu wykonaj kopię ustawień Minecrafta.

## Automatyczna kompilacja na GitHub
Wgraj cały projekt do repozytorium GitHub i otwórz Actions → Build NovaVisuals → Run workflow.
Po pomyślnym zakończeniu pobierz artefakt `NovaVisuals-jar` i skopiuj JAR do `mods`.
Uwaga: projekt nie został zweryfikowany kompilacją w tym środowisku.
