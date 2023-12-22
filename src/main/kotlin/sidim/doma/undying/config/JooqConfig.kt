package sidim.doma.undying.config

import java.sql.DriverManager
import java.sql.SQLException
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DefaultConfiguration
import org.jooq.impl.DefaultDSLContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JooqConfig {
    @Value("\${spring.datasource.username}")
    var userName: String = "username"

    @Value("\${spring.datasource.password}")
    var password: String = "password"

    @Value("\${spring.datasource.url}")
    var url: String = "jdbc:postgresql://localhost:1234/some_db"

    @Value("\${sql.dialect}")
    var jooqSqlDialect: String = "some-dialect"

    @Bean
    @Throws(SQLException::class)
    fun configuration(): DefaultConfiguration {
        val jooqConfiguration = DefaultConfiguration()
        jooqConfiguration.set(DriverManager.getConnection(url, userName, password))
        val dialect = SQLDialect.valueOf(jooqSqlDialect)
        jooqConfiguration.set(dialect)

        return jooqConfiguration
    }

    @Bean
    fun dslContext(configuration: org.jooq.Configuration?): DSLContext {
        return DefaultDSLContext(configuration)
    }
}