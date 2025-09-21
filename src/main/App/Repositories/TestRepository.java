package Repositories;

public class TestRepository extends Repository{
        public boolean INSERT(String script, Object[] params){
            boolean successful = makeInsert(script, params);
            printResult();
            return successful;
        }
        public boolean UPDATE(String script, Object[] params){
            boolean successful =makeUpdate(script,params);
            printResult();
            return successful;
        }
        public boolean RETRIEVE(String script, Object[] params){
            boolean successful = makeRetrieve(script,params);
            printResult();
            return successful;
        }
        private void printResult(){
            System.out.println("\n===========");
            System.out.println("Success: " + resultScript.isSuccess());
            System.out.println("Description: " +resultScript.getMessage());
            System.out.println("rowsAffected: " + resultScript.getRowsAffected());
            if(resultScript.getPayload() != null)
                System.out.println("payload: " + resultScript.getPayload().toString());
        }
}
