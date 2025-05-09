/*
 * Copyright 2024 circe
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.circe.pointer.literal

import io.circe.pointer.*
import munit.ScalaCheckSuite
import org.scalacheck.Prop

final class PointerInterpolatorCompileErrorsSuite extends ScalaCheckSuite {

  test("The pointer string interpolater should fail to compile on invalid literals") {
    assertNoDiff(
      compileErrors("pointer\"foo\""),
      s"""|error: Invalid JSON Pointer in interpolated string
          |      compileErrors("pointer\\"foo\\""),
          |                  ^
          |""".stripMargin
    )
  }

  test("The pointer string interpolater should fail with an interpolated relative distance") {
    assertNoDiff(
      compileErrors("""{val x = 1; pointer"$x/"}"""),
      s"""|error: Invalid JSON Pointer in interpolated string
          |      compileErrors(""\"{val x = 1; pointer"$$x/"}""\"),
          |                  ^
          |""".stripMargin
    )
  }

  test("The pointer string interpolater should fail with an empty interpolated relative distance") {
    assertNoDiff(
      compileErrors("""{val x = ""; pointer"$x/"}"""),
      s"""|error: Invalid JSON Pointer in interpolated string
          |      compileErrors(""\"{val x = ""; pointer"$$x/"}""\"),
          |                  ^
          |""".stripMargin
    )
  }
}
