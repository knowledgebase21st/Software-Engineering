DELIMITER //

CREATE FUNCTION my_vec_norm(v VECTOR) 
RETURNS FLOAT DETERMINISTIC
BEGIN
    DECLARE total FLOAT DEFAULT 0;
    DECLARE i INT DEFAULT 0;
    DECLARE dim INT;
    DECLARE j JSON DEFAULT CAST(VECTOR_TO_STRING(v) AS JSON);
    DECLARE val FLOAT;
    
    SET dim = JSON_LENGTH(j);
    
    WHILE i < dim DO
        SET val = JSON_EXTRACT(j, CONCAT('$[', i, ']'));
        SET total = total + (val * val);
        SET i = i + 1;
    END WHILE;
    
    RETURN SQRT(total);
END //

DELIMITER ;
