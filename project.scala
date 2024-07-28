//> using scala 3.4.2
//> using platform jvm

// dependencies
//> using dep "org.scalanlp::breeze::2.1.0"
//> using test.dep org.scalatest::scalatest::3.2.19

// these are all sensible defaults to catch annoying issues
//> using options -deprecation -unchecked -feature
//> using options -Xlint:nullary-unit -Xlint:infer-any -Xlint:unused -Xlint:nonlocal-return

// these are flags used by Scala native: if you aren't using scala-native, then they do nothing
// lto-thin has decent linking times, and release-fast does not too much optimisation.
//> using nativeLto thin
//> using nativeGc commix
//> using nativeMode release-fast