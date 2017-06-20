package br.com.newestapps.movie.data;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import br.com.newestapps.movie.utils.NetworkUtility;

public abstract class BaseRepository<T> {

    public static final int API = 1;
    public static final int LOCAL = 2;

    private Context context;

    public BaseRepository(Context context) {
        this.context = context;
    }

    public BaseRepository() {
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    /**
     * Retorna uma instancia do datasource de um repositório de escolha via "from"
     *
     * @param context        Contexto em execuÃ§Ã£o
     * @param repositoryType Classe da interface do repositÃ³rio para obter o datasource.
     * @param from           Tipo de datasource a ser instanciado (NETWORK ou LOCAL)
     * @return InstÃ¢ncia do datasource (null se nÃ£o existir)
     */
    public static Object newInstance(Context context, Class repositoryType, int from) {
        String repoName = repositoryType.getSimpleName();
        repoName = repoName.replace("Repository", "Datasource");
        String datasource = "br.com.newestapps.movie.data.datasource.";

        try {
            switch (from) {
                case API:
                    datasource += "Api" + repoName;
                    break;
                case LOCAL:
                    datasource += "Local" + repoName;
                    break;
                default:
                    throw new RuntimeException("Repository not found! Check \"from\" attribute!");
            }

            Constructor constructor = Class.forName(datasource).getConstructor(Context.class);

            Log.d("BaseRepository", "[" + from + "] Providing >> " + datasource);

            return constructor.newInstance(context);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            Log.d("BaseRepository", "Verifique se existe o constructor com Context");
        }

        return null;
    }

    /**
     * Retorna uma instÃ¢ncia do datasource mais adequado para a situaÃ§Ã£o.
     * <p>
     * REGRA:
     * - O usuÃ¡rio esta conectado em uma rede, com acesso a internet
     * - Data source NETWORK
     * - O usuÃ¡rio esta conectado em uma rede, mas o acesso a internet esta limitado
     * - Data source LOCAL
     * - O usuÃ¡rio nÃ£o esta conectado em nenhuma rede.
     * - Data source LOCAL
     *
     * @param context    Contexto em execuÃ§Ã£o
     * @param repository Classe do planejamento do repositÃ³rio para obter o datasource.
     * @return Data source de acordo com o estado atual da rede. NETWORK ou LOCAL
     */
    public static Object getProperRepository(Context context, Class repository) {
        Log.d("BaseRepository", "Checking proper repository...");
        int from = LOCAL;

        // Considera usar um repositório local de dados, caso internet nao esteja disponivel
        // mas apenas para infos de transmissão.
        if (NetworkUtility.isOnline(context)) {
            from = API;
        }

        return BaseRepository.newInstance(context, repository, from);
    }


    public static class RepositoryNotFound extends RuntimeException {
    }

    public static class RepositoryConnectionFailException extends RuntimeException {

        public RepositoryConnectionFailException(String message) {
            super(message);
        }

    }


}
