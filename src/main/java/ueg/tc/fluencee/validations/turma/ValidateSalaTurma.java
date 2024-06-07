package ueg.tc.fluencee.validations.turma;

import org.springframework.stereotype.Component;
import ueg.tc.fluencee.dto.TurmaRequestDTO;
import ueg.tc.fluencee.exception.BusinessException;
import ueg.tc.fluencee.exception.ErrorMessageCode;

@Component
public class ValidateSalaTurma implements IValidarTurmaBeforeSave{

    @Override
    public void validate(TurmaRequestDTO turmaRequestDTO) {
        validarSalaTurma(turmaRequestDTO.getSala());
    }

    @Override
    public void validate(TurmaRequestDTO turmaRequestDTO, Long id) {
        validarSalaTurma(turmaRequestDTO.getSala());
    }

    public static void validarSalaTurma(String sala) {
        if (sala != null) {
            if (!sala.isEmpty() && (sala.length() < 3 || sala.length() > 40)) {
                throw new BusinessException(ErrorMessageCode.CAMPOS_ERRADOS);
            }
        }
    }
}
