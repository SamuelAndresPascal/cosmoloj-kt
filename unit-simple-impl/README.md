# Simple Unit (implémentation Kotlin - implémentation de référence)

## Utilisation standard

L'utilisation standard se réfère aux méthodes implémentant la spécification Simple Unit.

Utilisation des unités transformées :

```kotlin
val m: AbstractUnit = SimpleFundamentalUnit()
val km: AbstractUnit = m.scaleMultiply(1000.0)
val cm: AbstractUnit = m.scaleDivide(100.0)
val cmToKm: UnitConverter = cm.getConverterTo(km)

cmToKm.convert(3.0) // 0.00003
cmToKm.inverse().convert(0.00003) // 3
```

Utilisation des unités dérivées :

```kotlin
val m: AbstractUnit = SimpleFundamentalUnit()
val km: AbstractUnit = m.scaleMultiply(1000.0)
val km2: AbstractUnit = SimpleDerivedUnit.of(km.factor(2))
val cm: AbstractUnit = m.scaleDivide(100.0)
val cm2: AbstractUnit = SimpleDerivedUnit.of(cm.factor(2))
val km2Tocm2: UnitConverter = km2.getConverterTo(cm2)

km2Tocm2.convert(3.0) // 30000000000
km2Tocm2.inverse().convert(30000000000) // 3
```

Utilisation des unités dérivées en combinant les dimensions :

```kotlin
val m: AbstractUnit = SimpleFundamentalUnit()
val kg: AbstractUnit = SimpleFundamentalUnit()
val g: AbstractUnit = kg.scaleDivide(1000.0)
val ton: AbstractUnit = kg.scaleMultiply(1000.0)
val gPerM2: AbstractUnit = SimpleDerivedUnit.of(g, m.factor(-2))
val km: AbstractUnit = m.scaleMultiply(1000.0)
val tonPerKm2: AbstractUnit = SimpleDerivedUnit.of(ton, km.factor(-2))
val cm: AbstractUnit = m.scaleDivide(100.0)
val tonPerCm2: AbstractUnit = SimpleDerivedUnit.of(ton, cm.factor(-2))
val gPerM2ToTonPerKm2: UnitConverter = gPerM2.getConverterTo(tonPerKm2)
val gPerM2ToTonPerCm2: UnitConverter = gPerM2.getConverterTo(tonPerCm2)

gPerM2ToTonPerKm2.convert(1.0) // 1
gPerM2ToTonPerKm2.inverse().convert(3.0) // 3
gPerM2ToTonPerCm2.convert(1.0) // 1e-4
gPerM2ToTonPerCm2.convert(3.0) // 3e-10
gPerM2ToTonPerCm2.offset() // 0.0
gPerM2ToTonPerCm2.scale() // 1e-10
gPerM2ToTonPerCm2.inverse().offset() // -0.0
gPerM2ToTonPerCm2.inverse().convert(3e-10) // 3
```

Utilisation des températures (conversions affines et linéaires) :

```kotlin
val k: AbstractUnit = SimpleFundamentalUnit()
val c: AbstractUnit = k.shift(273.15)
val kToC: UnitConverter = k.getConverterTo(c)

kToC.convert(0.0) // -273.15
kToC.inverse().convert(0.0) // 273.15

// en combinaison avec d'autres unités, les conversions d'unités de températures doivent devenir linéaires
val m: AbstractUnit = SimpleFundamentalUnit()
val cPerM: AbstractUnit = SimpleDerivedUnit.of(c, m.factor(-1))
val kPerM: AbstractUnit = SimpleDerivedUnit.of(k, m.factor(-1))
val kPerMToCPerM: UnitConverter = kPerM.getConverterTo(cPerM)

kPerMToCPerM.convert(3.0) // 3
kPerMToCPerM.inverse().convert(3.0) // 3
```

Utilisation des conversions non décimales :

```kotlin
val m: AbstractUnit = SimpleFundamentalUnit()
val km: AbstractUnit = m.scaleMultiply(1000.0)
val s: AbstractUnit = SimpleFundamentalUnit()
val h: AbstractUnit = s.scaleMultiply(3600.0)
val ms: AbstractUnit = SimpleDerivedUnit.of(m, s.factor(-1))
val kmh: AbstractUnit = SimpleDerivedUnit.of(km, h.factor(-1))
val msToKmh: UnitConverter = ms.getConverterTo(kmh)

msToKmh.convert(100.0) // 360.
msToKmh.inverse().convert(18.0) // 5
```

## Utilisation avec surcharge d'opérateurs

L'implémentation en Kotlin de Simple Unit utilise la surcharge d'opérateurs utilisable dans ce langage comme
alternative aux méthodes standards.

Utilisation des unités transformées :

```kotlin
val m: AbstractUnit = SimpleFundamentalUnit()
val km: AbstractUnit = m * 1000.0
val cm: AbstractUnit = m / 100.0
val cmToKm: UnitConverter = cm .. km

cmToKm.convert(3.0) // 0.00003
(!cmToKm).convert(0.00003) // 3
```

Utilisation des unités dérivées :

```kotlin
val m: AbstractUnit = SimpleFundamentalUnit()
val km: AbstractUnit = m * 1000.0
val km2: AbstractUnit = km % 2
val cm: AbstractUnit = m / 100.0
val cm2: AbstractUnit = cm % 2
val km2Tocm2: UnitConverter = km2 .. cm2

km2Tocm2.convert(3.0) // 30000000000
(!km2Tocm2).convert(30000000000) // 3
```

Utilisation des unités dérivées en combinant les dimensions :

```kotlin
val m: AbstractUnit = SimpleFundamentalUnit()
val kg: AbstractUnit = SimpleFundamentalUnit()
val g: AbstractUnit = kg / 1000.0
val ton: AbstractUnit = kg * 1000.0
val gPerM2: AbstractUnit = g / (m%2)
val km: AbstractUnit = m * 1000.0
val tonPerKm2: AbstractUnit = ton * (!km%2)
val cm: AbstractUnit = m / 100.0
val tonPerCm2: AbstractUnit = ton / (cm%2)
val gPerM2ToTonPerKm2: UnitConverter = gPerM2 .. tonPerKm2
val gPerM2ToTonPerCm2: UnitConverter = tonPerCm2 ..< gPerM2

gPerM2ToTonPerKm2.convert(1.0) // 1
(!gPerM2ToTonPerKm2).convert(3.0) // 3
gPerM2ToTonPerCm2.convert(1.0) // 1e-4
gPerM2ToTonPerCm2.convert(3.0) // 3e-10
gPerM2ToTonPerCm2.offset() // 0.0
gPerM2ToTonPerCm2.scale() // 1e-10
(!gPerM2ToTonPerCm2).offset() // -0.0
(!gPerM2ToTonPerCm2).convert(3e-10) // 3
```

Utilisation des températures (conversions affines et linéaires) :

```kotlin
val k: AbstractUnit = SimpleFundamentalUnit()
val c: AbstractUnit = k + 273.15
val kToC: UnitConverter = k.getConverterTo(c)

kToC.convert(0.0) // -273.15
(!kToC).convert(0.0) // 273.15

// en combinaison avec d'autres unités, les conversions d'unités de températures doivent devenir linéaires
val m: AbstractUnit = SimpleFundamentalUnit()
val cPerM: AbstractUnit = c / m
val kPerM: AbstractUnit = k / m
val kPerMToCPerM: UnitConverter = kPerM .. cPerM

kPerMToCPerM.convert(3.0) // 3
(!kPerMToCPerM).convert(3.0) // 3
```

Utilisation des conversions non décimales :

```kotlin
val m: AbstractUnit = SimpleFundamentalUnit()
val km: AbstractUnit = m * 1000.0
val s: AbstractUnit = SimpleFundamentalUnit()
val h: AbstractUnit = s * 3600.0
val ms: AbstractUnit = m / s
val kmh: AbstractUnit = km / h
val msToKmh: UnitConverter = ms .. kmh

msToKmh.convert(100.0) // 360.
(!msToKmh).convert(18.0) // 5
```
