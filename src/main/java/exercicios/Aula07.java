package exercicios;

import exercicios.base.Aula;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Esta é uma classe para você poder implementar as atividades propostas no README.
 * Você <b>NÃO</b> deve alterar:
 * <ul>
 *     <li>a estrutura deste arquivo;</li>
 *     <li>o nome da classe, dos métodos ou dos atributos;</li>
 *     <li>parâmetros e tipo de retorno dos métodos.</li>
 * </ul>
 *
 * <b>Mas você PRECISA alterar valores dos atributos existentes</b>.
 *
 * <p>Você pode alterar o código interno dos métodos, criar métodos auxiliares que podem ser chamados
 * pelos existentes, mas não deve alterar a estrutura dos métodos disponíveis.</p>
 *
 * @author Manoel Campos da Silva Filho
 */
public class Aula07 extends Aula {
    /**
     * {@link Predicate<Estudante>} que seleciona somente as mulheres
     * matriculadas em algum curso e com nota maior ou igual a 6.
     * Este deve ser um predicado composto usando {@link Predicate#and(Predicate)}.
     * Você deve trocar o valor armazenado ao atributo para ele seguir a regra definida acima.
     */
    private final Predicate<Estudante> mulheresAprovadas =
            ((Predicate<Estudante>) Estudante::isMulher)
                    .and(Estudante::hasCurso)
                    .and(Estudante::isAprovado);

    /**
     * Comparador base que ordena os estudantes pelo nome do curso de forma crescente.
     * É reutilizado para compor os demais {@link Comparator} compostos da classe.
     */
    private final Comparator<Estudante> comparadorPorCurso =
            Comparator.comparing(e -> e.getCurso().getNome());

    /**
     * Comparador base que ordena os estudantes pela nota de forma crescente.
     * É reutilizado para compor os demais {@link Comparator} compostos da classe.
     */
    private final Comparator<Estudante> comparadorPorNota =
            Comparator.comparingDouble(Estudante::getNota);

    /**
     * Comparador composto: nome do curso crescente e nota crescente.
     */
    private final Comparator<Estudante> porCursoCrescenteAndNotaCrescente =
            comparadorPorCurso.thenComparing(comparadorPorNota);

    /**
     * Comparador composto: nome do curso decrescente e nota crescente.
     */
    private final Comparator<Estudante> porCursoDecrescenteAndNotaCrescente =
            comparadorPorCurso.reversed().thenComparing(comparadorPorNota);

    /**
     * Comparador composto: nome do curso crescente e nota decrescente.
     */
    private final Comparator<Estudante> porCursoCrescenteAndNotaDecrescente =
            comparadorPorCurso.thenComparing(comparadorPorNota.reversed());

    /**
     * Comparador composto: nome do curso decrescente e nota decrescente.
     */
    private final Comparator<Estudante> totalmenteDecrescente =
            comparadorPorCurso.reversed().thenComparing(comparadorPorNota.reversed());

    /**
     * Você pode chamar os métodos existentes e outros que você criar aqui,
     * incluir prints e fazer o que desejar neste método para conferir os valores retornados pelo seu método.
     * Para verificar se sua implementação está correta, clique com o botão direito no nome do projeto na aba esquerda
     * do IntelliJ e selecione a opção "Run All Tests".
     */
    public Aula07() {
        System.out.println("\n=== Mulheres aprovadas (ordem original) ===");
        getEstudantesMulheresAprovadasNaoOrdenadasModificavel().forEach(System.out::println);

        System.out.println("\n=== Mulheres aprovadas (lista não-modificável) ===");
        getEstudantesMulheresAprovadas().forEach(System.out::println);

        System.out.println("\n=== Mulheres aprovadas: curso crescente e nota crescente ===");
        getEstudantesMulheresAprovadasOrdenadasPorCursoAndNota().forEach(System.out::println);

        System.out.println("\n=== Mulheres aprovadas: curso decrescente e nota crescente ===");
        getEstudantesMulheresAprovadasOrdenadasPorCursoDecrescenteAndNotaCrescente().forEach(System.out::println);

        System.out.println("\n=== Mulheres aprovadas: curso decrescente e nota decrescente ===");
        getEstudantesMulheresAprovadasOrdenadasTotalmenteDecrescente().forEach(System.out::println);

        System.out.println("\n=== Mulheres aprovadas: curso crescente e nota decrescente ===");
        getEstudantesMulheresAprovadasOrdenadasPorCursoCrescenteAndNotaDecrescente().forEach(System.out::println);
    }

    /**
     * Veja o método construtor {@link #Aula06()}.
     */
    public static void main(String[] args) {
        new Aula07();
    }

    /**
     * Obtém uma {@link Stream} dos estudantes que satisfazem o predicado composto
     * {@link #mulheresAprovadas}. Método auxiliar reutilizado pelos demais métodos da classe.
     *
     * @return uma {@link Stream} com as mulheres matriculadas em algum curso e aprovadas
     */
    protected Stream<Estudante> streamMulheresAprovadas() {
        return estudantes.stream().filter(mulheresAprovadas);
    }

    /**
     * Obtém uma Lista <b>NÃO-MODIFICÁVEL</b> de mulheres matriculadas e aprovadas em algum curso
     * O método usa o predicado {@link #mulheresAprovadas} para filtrar a lista de estudantes.
     * Desta forma, você precisa definir um predicado composto com {@link Predicate#and(Predicate)}
     * para tal atributo.
     *
     * @return uma Lista <b>NÃO-MODIFICÁVEL</b> de estudantes selecionados pelo predicado {@link #mulheresAprovadas}
     */
    public List<Estudante> getEstudantesMulheresAprovadas() {
        return streamMulheresAprovadas().toList();
    }

    /**
     * Obtém uma Lista com os mesmos filtros do método {@link #getEstudantesMulheresAprovadas()},
     * mas ordenada por curso e nota.
     *
     * @return uma Lista <b>NÃO-MODIFICÁVEL</b> de estudantes selecionados pelo predicado {@link #mulheresAprovadas}
     */
    public List<Estudante> getEstudantesMulheresAprovadasOrdenadasPorCursoAndNota() {
        return streamMulheresAprovadas().sorted(porCursoCrescenteAndNotaCrescente).toList();
    }

    /**
     * Obtém uma Lista com os mesmos filtros do método {@link #getEstudantesMulheresAprovadas()},
     * mas ordenada de forma decrescente pelo nome do curso e crescente pela nota.
     *
     * @return uma Lista <b>NÃO-MODIFICÁVEL</b> de estudantes selecionados pelo predicado {@link #mulheresAprovadas}
     */
    public List<Estudante> getEstudantesMulheresAprovadasOrdenadasPorCursoDecrescenteAndNotaCrescente() {
        return streamMulheresAprovadas().sorted(porCursoDecrescenteAndNotaCrescente).toList();
    }

    /**
     * Obtém uma Lista com os mesmos filtros do método {@link #getEstudantesMulheresAprovadas()},
     * mas na ordem original retornada pela Stream.
     * A lista deve ser <b>MODIFICÁVEL</b>.
     *
     * @return uma Lista <b>MODIFICÁVEL</b> de estudantes selecionados pelo predicado {@link #mulheresAprovadas}
     */
    public List<Estudante> getEstudantesMulheresAprovadasNaoOrdenadasModificavel() {
        return streamMulheresAprovadas().collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Obtém uma Lista com os mesmos filtros do método {@link #getEstudantesMulheresAprovadas()},
     * mas ordenada de forma decrescente tanto pelo nome do curso quanto pela nota.
     *
     * @return uma Lista <b>NÃO-MODIFICÁVEL</b> de estudantes selecionados pelo predicado {@link #mulheresAprovadas}
     */
    public List<Estudante> getEstudantesMulheresAprovadasOrdenadasTotalmenteDecrescente() {
        return streamMulheresAprovadas().sorted(totalmenteDecrescente).toList();
    }

    /**
     * Obtém uma Lista com os mesmos filtros do método {@link #getEstudantesMulheresAprovadas()},
     * mas ordenada de forma crescente pelo nome do curso e descrecente pela nota.
     *
     * @return uma Lista <b>NÃO-MODIFICÁVEL</b> de estudantes selecionados pelo predicado {@link #mulheresAprovadas}
     */
    public List<Estudante> getEstudantesMulheresAprovadasOrdenadasPorCursoCrescenteAndNotaDecrescente() {
        return streamMulheresAprovadas().sorted(porCursoCrescenteAndNotaDecrescente).toList();
    }
}
