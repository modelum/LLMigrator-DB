package com.umu.prompts.infrastructure.api.rest.dto.validators;

import static com.umu.prompts.infrastructure.api.rest.dto.enums.FrameworkTypeDto.*;
import static com.umu.prompts.infrastructure.api.rest.dto.enums.ProgrammingLanguageDto.*;

import com.umu.prompts.infrastructure.api.rest.dto.CodeMigrationRequestDto;
import com.umu.prompts.infrastructure.api.rest.dto.enums.FrameworkTypeDto;
import com.umu.prompts.infrastructure.api.rest.dto.enums.ProgrammingLanguageDto;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CodeMigrationRequestDtoValidator implements Validator {

    // Mapa que define la compatibilidad entre lenguajes y frameworks.
    private static final Map<ProgrammingLanguageDto, Set<FrameworkTypeDto>> LANGUAGE_FRAMEWORK_MAP = Map.ofEntries(
            Map.entry(JAVA, Set.of(
                    SPRING_DATA_JPA, SPRING_DATA_JDBC, SPRING_DATA_R2DBC, SPRING_DATA_MONGODB,
                    SPRING_DATA_CASSANDRA, SPRING_DATA_ELASTICSEARCH, SPRING_DATA_REDIS,
                    JOOQ, MYBATIS, JDBI, HIBERNATE_ORM, ECLIPSELINK_ORM, DATANUCLEUS,
                    OBJECTDB_FRAMEWORK, QUARKUS_PANACHE, MICRONAUT_DATA, JDBC
            )),
            Map.entry(DOTNET, Set.of(
                    ENTITY_FRAMEWORK_CORE, NHIBERNATE, DAPPER, LINQ_TO_SQL, ADO_NET
            )),
            Map.entry(JAVASCRIPT, Set.of(
                    TYPEORM, SEQUELIZE, MIKRO_ORM, PRISMA, KNEX, OBJECTION_JS, MONGOOSE, WATERLINE,
                    FIREBASE_FIRESTORE_SDK, SUPABASE_CLIENT, PLANETSCALE_CLIENT, AWS_AMPLIFY_DATASOURCE
            )),
            // Asumimos que TypeScript usa los mismos frameworks que JavaScript
            Map.entry(TYPESCRIPT, Set.of(
                    TYPEORM, SEQUELIZE, MIKRO_ORM, PRISMA, KNEX, OBJECTION_JS, MONGOOSE, WATERLINE,
                    FIREBASE_FIRESTORE_SDK, SUPABASE_CLIENT, PLANETSCALE_CLIENT, AWS_AMPLIFY_DATASOURCE
            )),
            Map.entry(PYTHON, Set.of(
                    SQLALCHEMY, PEEWEE, TORTOISE_ORM, PONY_ORM, DJANGO_ORM, MONGOENGINE, ODMANTIC
            )),
            Map.entry(RUBY, Set.of(
                    ACTIVE_RECORD, SEQUEL_RB
            )),
            Map.entry(GO, Set.of(
                    GORM_GO, ENT_GO, BEEGO_ORM
            )),
            Map.entry(PHP, Set.of(
                    ELOQUENT, DOCTRINE_ORM, CAKEPHP_ORM
            ))
            // OTROS no se incluye explícitamente si no hay frameworks específicos listados para él
    );


    @Override
    public boolean supports(Class<?> clazz) {
        // Este validador soporta la clase CodeMigrationRequestDto
        return CodeMigrationRequestDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CodeMigrationRequestDto dto = (CodeMigrationRequestDto) target;

        ProgrammingLanguageDto sourceLanguage = dto.getSourceLanguage();
        FrameworkTypeDto sourceFramework = dto.getSourceFramework();
        ProgrammingLanguageDto targetLanguage = dto.getTargetLanguage();
        FrameworkTypeDto targetFramework = dto.getTargetFramework();

        // Validar la combinación de lenguaje fuente y framework fuente
        if (sourceLanguage != null && sourceFramework != null) {
            Set<FrameworkTypeDto> supportedFrameworks = LANGUAGE_FRAMEWORK_MAP.get(sourceLanguage);
            if (supportedFrameworks == null || !supportedFrameworks.contains(sourceFramework)) {
                errors.rejectValue("sourceFramework", "FrameworkLanguageMismatch.source",
                        String.format("El framework fuente '%s' no es compatible con el lenguaje fuente '%s'.",
                                sourceFramework.toString(), sourceLanguage.toString()));
            }
        }

        // Validar la combinación de lenguaje destino y framework destino
        if (targetLanguage != null && targetFramework != null) {
            Set<FrameworkTypeDto> supportedFrameworks = LANGUAGE_FRAMEWORK_MAP.get(targetLanguage);
            if (supportedFrameworks == null || !supportedFrameworks.contains(targetFramework)) {
                errors.rejectValue("targetFramework", "FrameworkLanguageMismatch.target",
                        String.format("El framework destino '%s' no es compatible con el lenguaje destino '%s'.",
                                targetFramework.toString(), targetLanguage.toString()));
            }
        }
    }
}
