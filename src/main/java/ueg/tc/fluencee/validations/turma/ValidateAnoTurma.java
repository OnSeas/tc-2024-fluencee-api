package ueg.tc.fluencee.validations.turma;

import org.springframework.stereotype.Component;
import ueg.tc.fluencee.dto.TurmaRequestDTO;
import ueg.tc.fluencee.exception.BusinessException;
import ueg.tc.fluencee.exception.ErrorMessageCode;

@Component
public class ValidateAnoTurma implements IValidarTurmaBeforeSave{
    @Override
    public void validate(TurmaRequestDTO turmaRequestDTO) {
        validarAnoTurma(turmaRequestDTO.getAno());
    }

    @Override
    public void validate(TurmaRequestDTO turmaRequestDTO, Long id) {
        validarAnoTurma(turmaRequestDTO.getAno());
    }

    public static void validarAnoTurma(String ano) {
        if (ano != null) {
            if (!ano.isEmpty() && (ano.length() < 3 || ano.length() > 40)) {
                throw new BusinessException(ErrorMessageCode.CAMPOS_ERRADOS);
            }
        }
    }
}
