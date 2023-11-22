CREATE OR REPLACE FUNCTION update_answers_count()
    RETURNS TRIGGER AS
$$
BEGIN
    IF TG_OP = 'INSERT' THEN
        UPDATE questions
        SET answers_count         = (SELECT COUNT(*) FROM answer_validations WHERE question_id = NEW.question_id),
            correct_answers_count = (SELECT COUNT(*)
                                     FROM answer_validations
                                     WHERE question_id = NEW.question_id AND points > 0)
        WHERE id = NEW.question_id;
        RETURN NEW;
    ELSE
        UPDATE questions
        SET answers_count         = (SELECT COUNT(*) FROM answer_validations WHERE question_id = OLD.question_id),
            correct_answers_count = (SELECT COUNT(*)
                                     FROM answer_validations
                                     WHERE question_id = OLD.question_id AND points > 0)
        WHERE id = OLD.question_id;
        RETURN OLD;
    END IF;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER answers_count_trigger
    AFTER INSERT OR DELETE
    ON answer_validations
    FOR EACH ROW
EXECUTE PROCEDURE update_answers_count();
