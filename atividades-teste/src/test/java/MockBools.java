import br.ufal.ic.atividades.teste.Bools;

public class MockBools extends Bools {

	@Override
	public int checkElementIndex(int index, int size, String desc) {
		return index;
	}

	@Override
	public <T> T checkNotNull(T reference) {
		return reference;
	}
}
