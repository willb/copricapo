package com.freevariable.copricapo.schema
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = scala.slick.driver.PostgresDriver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: scala.slick.driver.JdbcProfile
  import profile.simple._
  import scala.slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import scala.slick.jdbc.{GetResult => GR}
  
  /** DDL for all tables. Call .create to execute. */
  lazy val ddl = AlembicVersion.ddl ++ Messages.ddl ++ Package.ddl ++ PackageMessages.ddl ++ User.ddl ++ UserMessages.ddl
  
  /** Entity class storing rows of table AlembicVersion
   *  @param versionNum Database column version_num  */
  case class AlembicVersionRow(versionNum: String)
  /** GetResult implicit for fetching AlembicVersionRow objects using plain SQL queries */
  implicit def GetResultAlembicVersionRow(implicit e0: GR[String]): GR[AlembicVersionRow] = GR{
    prs => import prs._
    AlembicVersionRow(<<[String])
  }
  /** Table description of table alembic_version. Objects of this class serve as prototypes for rows in queries. */
  class AlembicVersion(tag: Tag) extends Table[AlembicVersionRow](tag, "alembic_version") {
    def * = versionNum <> (AlembicVersionRow, AlembicVersionRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = versionNum.?.shaped.<>(r => r.map(_=> AlembicVersionRow(r.get)), (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column version_num  */
    val versionNum: Column[String] = column[String]("version_num")
  }
  /** Collection-like TableQuery object for table AlembicVersion */
  lazy val AlembicVersion = new TableQuery(tag => new AlembicVersion(tag))
  
  /** Entity class storing rows of table Messages
   *  @param id Database column id AutoInc, PrimaryKey
   *  @param i Database column i 
   *  @param timestamp Database column timestamp 
   *  @param certificate Database column certificate 
   *  @param signature Database column signature 
   *  @param topic Database column topic 
   *  @param _Msg Database column _msg 
   *  @param category Database column category 
   *  @param sourceName Database column source_name 
   *  @param sourceVersion Database column source_version 
   *  @param msgId Database column msg_id  */
  case class MessagesRow(id: Int, i: Int, timestamp: java.sql.Timestamp, certificate: Option[String], signature: Option[String], topic: Option[String], _Msg: String, category: Option[String], sourceName: Option[String], sourceVersion: Option[String], msgId: Option[String])
  /** GetResult implicit for fetching MessagesRow objects using plain SQL queries */
  implicit def GetResultMessagesRow(implicit e0: GR[Int], e1: GR[java.sql.Timestamp], e2: GR[Option[String]], e3: GR[String]): GR[MessagesRow] = GR{
    prs => import prs._
    MessagesRow.tupled((<<[Int], <<[Int], <<[java.sql.Timestamp], <<?[String], <<?[String], <<?[String], <<[String], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table messages. Objects of this class serve as prototypes for rows in queries. */
  class Messages(tag: Tag) extends Table[MessagesRow](tag, "messages") {
//    def * = (id, i, timestamp, certificate, signature, topic, _Msg, category, sourceName, sourceVersion, msgId) <> (MessagesRow.tupled, MessagesRow.unapply)
    def * = (id, i, timestamp, None, None, topic, _Msg, category, sourceName, sourceVersion, msgId) <> (MessagesRow.tupled, MessagesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (id.?, i.?, timestamp.?, certificate, signature, topic, _Msg.?, category, sourceName, sourceVersion, msgId).shaped.<>({r=>import r._; _1.map(_=> MessagesRow.tupled((_1.get, _2.get, _3.get, _4, _5, _6, _7.get, _8, _9, _10, _11)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column id AutoInc, PrimaryKey */
    val id: Column[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column i  */
    val i: Column[Int] = column[Int]("i")
    /** Database column timestamp  */
    val timestamp: Column[java.sql.Timestamp] = column[java.sql.Timestamp]("timestamp")
    /** Database column certificate  */
    val certificate: Column[Option[String]] = column[Option[String]]("certificate")
    /** Database column signature  */
    val signature: Column[Option[String]] = column[Option[String]]("signature")
    /** Database column topic  */
    val topic: Column[Option[String]] = column[Option[String]]("topic")
    /** Database column _msg  */
    val _Msg: Column[String] = column[String]("_msg")
    /** Database column category  */
    val category: Column[Option[String]] = column[Option[String]]("category")
    /** Database column source_name  */
    val sourceName: Column[Option[String]] = column[Option[String]]("source_name")
    /** Database column source_version  */
    val sourceVersion: Column[Option[String]] = column[Option[String]]("source_version")
    /** Database column msg_id  */
    val msgId: Column[Option[String]] = column[Option[String]]("msg_id")
    
    /** Index over (timestamp) (database name index_msg_timestamp) */
    val index1 = index("index_msg_timestamp", timestamp)
    /** Uniqueness Index over (msgId) (database name messages_msg_id_key) */
    val index2 = index("messages_msg_id_key", msgId, unique=true)
  }
  /** Collection-like TableQuery object for table Messages */
  lazy val Messages = new TableQuery(tag => new Messages(tag))
  
  /** Entity class storing rows of table Package
   *  @param name Database column name PrimaryKey */
  case class PackageRow(name: String)
  /** GetResult implicit for fetching PackageRow objects using plain SQL queries */
  implicit def GetResultPackageRow(implicit e0: GR[String]): GR[PackageRow] = GR{
    prs => import prs._
    PackageRow(<<[String])
  }
  /** Table description of table package. Objects of this class serve as prototypes for rows in queries. */
  class Package(tag: Tag) extends Table[PackageRow](tag, "package") {
    def * = name <> (PackageRow, PackageRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = name.?.shaped.<>(r => r.map(_=> PackageRow(r.get)), (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column name PrimaryKey */
    val name: Column[String] = column[String]("name", O.PrimaryKey)
  }
  /** Collection-like TableQuery object for table Package */
  lazy val Package = new TableQuery(tag => new Package(tag))
  
  /** Entity class storing rows of table PackageMessages
   *  @param `package` Database column package 
   *  @param msg Database column msg  */
  case class PackageMessagesRow(`package`: Option[String], msg: Option[Int])
  /** GetResult implicit for fetching PackageMessagesRow objects using plain SQL queries */
  implicit def GetResultPackageMessagesRow(implicit e0: GR[Option[String]], e1: GR[Option[Int]]): GR[PackageMessagesRow] = GR{
    prs => import prs._
    PackageMessagesRow.tupled((<<?[String], <<?[Int]))
  }
  /** Table description of table package_messages. Objects of this class serve as prototypes for rows in queries.
   *  NOTE: The following names collided with Scala keywords and were escaped: package */
  class PackageMessages(tag: Tag) extends Table[PackageMessagesRow](tag, "package_messages") {
    def * = (`package`, msg) <> (PackageMessagesRow.tupled, PackageMessagesRow.unapply)
    
    /** Database column package 
     *  NOTE: The name was escaped because it collided with a Scala keyword. */
    val `package`: Column[Option[String]] = column[Option[String]]("package")
    /** Database column msg  */
    val msg: Column[Option[Int]] = column[Option[Int]]("msg")
    
    /** Foreign key referencing Messages (database name package_messages_msg_fkey) */
    lazy val messagesFk = foreignKey("package_messages_msg_fkey", msg, Messages)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Package (database name package_messages_package_fkey) */
    lazy val packageFk = foreignKey("package_messages_package_fkey", `package`, Package)(r => r.name, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table PackageMessages */
  lazy val PackageMessages = new TableQuery(tag => new PackageMessages(tag))
  
  /** Entity class storing rows of table User
   *  @param name Database column name PrimaryKey */
  case class UserRow(name: String)
  /** GetResult implicit for fetching UserRow objects using plain SQL queries */
  implicit def GetResultUserRow(implicit e0: GR[String]): GR[UserRow] = GR{
    prs => import prs._
    UserRow(<<[String])
  }
  /** Table description of table user. Objects of this class serve as prototypes for rows in queries. */
  class User(tag: Tag) extends Table[UserRow](tag, "user") {
    def * = name <> (UserRow, UserRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = name.?.shaped.<>(r => r.map(_=> UserRow(r.get)), (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column name PrimaryKey */
    val name: Column[String] = column[String]("name", O.PrimaryKey)
  }
  /** Collection-like TableQuery object for table User */
  lazy val User = new TableQuery(tag => new User(tag))
  
  /** Entity class storing rows of table UserMessages
   *  @param username Database column username 
   *  @param msg Database column msg  */
  case class UserMessagesRow(username: Option[String], msg: Option[Int])
  /** GetResult implicit for fetching UserMessagesRow objects using plain SQL queries */
  implicit def GetResultUserMessagesRow(implicit e0: GR[Option[String]], e1: GR[Option[Int]]): GR[UserMessagesRow] = GR{
    prs => import prs._
    UserMessagesRow.tupled((<<?[String], <<?[Int]))
  }
  /** Table description of table user_messages. Objects of this class serve as prototypes for rows in queries. */
  class UserMessages(tag: Tag) extends Table[UserMessagesRow](tag, "user_messages") {
    def * = (username, msg) <> (UserMessagesRow.tupled, UserMessagesRow.unapply)
    
    /** Database column username  */
    val username: Column[Option[String]] = column[Option[String]]("username")
    /** Database column msg  */
    val msg: Column[Option[Int]] = column[Option[Int]]("msg")
    
    /** Foreign key referencing Messages (database name user_messages_msg_fkey) */
    lazy val messagesFk = foreignKey("user_messages_msg_fkey", msg, Messages)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing User (database name user_messages_username_fkey) */
    lazy val userFk = foreignKey("user_messages_username_fkey", username, User)(r => r.name, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table UserMessages */
  lazy val UserMessages = new TableQuery(tag => new UserMessages(tag))
}