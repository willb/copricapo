package com.freevariable.copricapo

import scala.slick.direct._
import scala.slick.direct.AnnotationMapper._
import scala.slick.driver.PostgresDriver
import scala.slick.driver.PostgresDriver.simple._
import scala.reflect.runtime.universe
import scala.slick.jdbc.StaticQuery.interpolation

object Importer {
  /* Table "public.messages"
   *       Column     |            Type             |                       Modifiers                       
   *  ----------------+-----------------------------+-------------------------------------------------------
   *   id             | integer                     | not null default nextval('messages_id_seq'::regclass)
   *   i              | integer                     | not null
   *   timestamp      | timestamp without time zone | not null
   *   certificate    | text                        | 
   *   signature      | text                        | 
   *   topic          | text                        | 
   *   _msg           | text                        | not null
   *   category       | text                        | 
   *   source_name    | text                        | 
   *   source_version | text                        | 
   *   msg_id         | text                        | 
   * Indexes:
   *     "messages_pkey" PRIMARY KEY, btree (id)
   *     "messages_msg_id_key" UNIQUE CONSTRAINT, btree (msg_id)
   *     "index_msg_timestamp" btree ("timestamp")
   * Referenced by:
   *     TABLE "package_messages" CONSTRAINT "package_messages_msg_fkey" FOREIGN KEY (msg) REFERENCES messages(id)
   *     TABLE "user_messages" CONSTRAINT "user_messages_msg_fkey" FOREIGN KEY (msg) REFERENCES messages(id)
   */
  
  /* Table "public.package_messages"
   *   Column  |  Type   | Modifiers 
   *  ---------+---------+-----------
   *   package | text    | 
   *   msg     | integer | 
   * Foreign-key constraints:
   *   "package_messages_msg_fkey" FOREIGN KEY (msg) REFERENCES messages(id)
   *   "package_messages_package_fkey" FOREIGN KEY (package) REFERENCES package(name)
   */

  /* Table "public.user_messages"
   *   Column  |  Type   | Modifiers 
   * ----------+---------+-----------
   *  username | text    | 
   *  msg      | integer | 
   * Foreign-key constraints:
   *     "user_messages_msg_fkey" FOREIGN KEY (msg) REFERENCES messages(id)
   *     "user_messages_username_fkey" FOREIGN KEY (username) REFERENCES "user"(name)
   */

  /* Table "public.package"
   *  Column | Type | Modifiers 
   * --------+------+-----------
   *  name   | text | not null
   * Indexes:
   *     "package_pkey" PRIMARY KEY, btree (name)
   * Referenced by:
   *     TABLE "package_messages" CONSTRAINT "package_messages_package_fkey" FOREIGN KEY (package) REFERENCES package(name)
   */

  /* Table "public.user"
   *  Column | Type | Modifiers 
   * --------+------+-----------
   *  name   | text | not null
   * Indexes:
   *     "user_pkey" PRIMARY KEY, btree (name)
   * Referenced by:
   *     TABLE "user_messages" CONSTRAINT "user_messages_username_fkey" FOREIGN KEY (username) REFERENCES "user"(name)
   */
  
  def sandbox(q: String) {
    // val backend = new SlickBackend(PostgresDriver, AnnotationMapper)
    //
    // Database.forURL("jdbc:postgresql:datanommer", driver = "org.postgresql.Driver") withSession { implicit session:Session =>
    //   // insert using SQL (currently not supported by direct embedding)
    //   import slick.jdbc.StaticQuery.interpolation
    // }
  }
}