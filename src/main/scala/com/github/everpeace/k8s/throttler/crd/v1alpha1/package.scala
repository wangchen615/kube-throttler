/*
 * Copyright 2018 Shingo Omura <https://github.com/everpeace>
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

package com.github.everpeace.k8s.throttler.crd

import skuber.Resource.ResourceList
import skuber.{CustomResource, ListResource}

package object v1alpha1 {
  type Throttle     = CustomResource[v1alpha1.Throttle.Spec, v1alpha1.Throttle.Status]
  type ThrottleList = ListResource[Throttle]

  type ClusterThrottle =
    CustomResource[v1alpha1.ClusterThrottle.Spec, v1alpha1.ClusterThrottle.Status]
  type ClusterThrottleList = ListResource[ClusterThrottle]

  case class ResourceAmount(
      podsCount: Option[Int] = None,
      resourceRequests: ResourceList = Map.empty)

  case class IsResourceThrottled(
      podsCount: Option[Boolean] = None,
      resourceRequests: Map[String, Boolean] = Map.empty)

  trait CommonJsonFormat {
    import play.api.libs.json._
    import skuber.json.format._

    implicit val throttleResourceAmountFmt: Format[v1alpha1.ResourceAmount] =
      Json.format[v1alpha1.ResourceAmount]

    implicit val throttleIsResourceThrottleFmt: Format[v1alpha1.IsResourceThrottled] =
      Json.format[v1alpha1.IsResourceThrottled]
  }

  object Implicits
      extends v1alpha1.Throttle.Implicits
      with v1alpha1.ClusterThrottle.Implicits
      with CommonJsonFormat

}
