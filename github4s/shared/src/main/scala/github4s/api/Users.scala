/*
 * Copyright 2016-2017 47 Degrees, LLC. <http://www.47deg.com>
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

package github4s.api

import github4s.GithubResponses.GHResponse
import github4s.{Config, GithubApiUrls, HttpClient, HttpRequestBuilderExtension}
import github4s.free.domain.{Capture, Pagination, User}
import io.circe.generic.auto._

/** Factory to encapsulate calls related to Users operations  */
class Users[C, M[_]](
    implicit urls: GithubApiUrls,
    C: Capture[M],
    httpClientImpl: HttpRequestBuilderExtension[C, M]) {

  val httpClient = new HttpClient[C, M]

  /**
   * Get information for a particular user
   *
   * @param config accessToken (to identify the authenticated user) and headers (optional user
   *               headers to include in the request)
   * @param username of the user to retrieve
   * @return GHResponse[User] User details
   */
  def get(config: Config, username: String): M[GHResponse[User]] =
    httpClient.get[User](config.accessToken, s"users/$username", config.headers)

  /**
   * Get information of the authenticated user
   * @param config accessToken (to identify the authenticated user) and headers (optional user
   *               headers to include in the request)
   * @return GHResponse[User] User details
   */
  def getAuth(config: Config): M[GHResponse[User]] =
    httpClient.get[User](config.accessToken, "user", config.headers)

  /**
   * Get users
   *
   * @param config accessToken (to identify the authenticated user) and headers (optional user
   *               headers to include in the request)
   * @param since The integer ID of the last User that you've seen.
   * @param pagination Limit and Offset for pagination
   * @return GHResponse[List[User] ] List of user's details
   */
  def getUsers(
      config: Config,
      since: Int,
      pagination: Option[Pagination] = None
  ): M[GHResponse[List[User]]] =
    httpClient
      .get[List[User]](
        config.accessToken,
        "users",
        config.headers,
        Map("since" → since.toString),
        pagination)

}