package ueg.tc.fluencee.validations.turma;

import ueg.tc.fluencee.dto.TurmaRequestDTO;

public interface IValidarTurmaBeforeSave {
    public void validate(TurmaRequestDTO turmaRequestDTO);

    public void validate(TurmaRequestDTO turmaRequestDTO, Long id);
}
