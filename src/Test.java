

public class Test {
	public static void main(String[] args) {
	    Options options = new Options();
	    options.addOption("myCmd", "myCommand", false, "will run myCommand()." );
	    options.addOption("helloW", "helloWord", true, "display hello word the number of time specify." );
	 
	    try{
	        CommandLine line = new BasicParser().parse( options, args );
	 
	        if( line.hasOption( "myCommand" ) ) {
	            myCommand();
	        }
	 
	        if(line.hasOption("helloWord")){
	            String repeat = line.getOptionValue("helloWord");
	            Integer repeatInt = new Integer(repeat);
	            for(int i =0; i<repeatInt; i++){
	                System.out.println( "Hello word !");
	            }
	        }
	 
	    }catch( ParseException exp ) {
	        System.out.println( "Unexpected exception:" + exp.getMessage() );
	    }
	}
	 
	public static void myCommand(){
	    System.out.println("myCommand() just get call");
	}
}
