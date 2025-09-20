package main.App.Repositories;

public class TestRepository extends Repository{
        public boolean INSERT(String script, Object[] params){
            boolean successful = makeDml(script, params);
            printResult();
            return successful;
        }
        public boolean UPDATE(String script, Object[] params){
            boolean successful =makeDml(script,params);
            printResult();
            return successful;
        }
        public boolean DELETE(String script, Object[] params){
            boolean successful =makeDml(script,params);
            printResult();
            return successful;
        }
        public boolean RETRIEVE(String script, Object[] params){
            boolean successful = makeQuery(script,params);
            printResult();
            return successful;
        }
        private void printResult(){
            System.out.println("Sucess: " + resultScript.isSuccess());
            System.out.println("Descripcion: " +resultScript.getMessage());
            System.out.println("CodeError: " + resultScript.getErrorCode());
            System.out.println("rowsAffected: " + resultScript.getRowsAffected());
            if(resultScript.getPayload() != null)
                System.out.println("payload: " + resultScript.getPayload().toString());
        }
}
