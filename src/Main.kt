import kotlin.concurrent.thread
import kotlin.random.Random

fun main(){
    val DAUS: String = "⚀ ⚁ ⚂ ⚃ ⚄ ⚅"
    val CARES_DAU: Array<String> = arrayOf("⚀", "⚁", "⚂", "⚃", "⚄", "⚅")

    var partides: Int?
    var tiradesPerPartida: Int?
    var tiradaCPU: Int
    var partidasGanadasPerUsuario : Int = 0
    var partidasGanadasPerCPU : Int = 0

    println("""    
        ${DAUS}
    ╔════════════════╗
    ║ JOC DELS DAUS  ║
    ╚════════════════╝
(Per guanyar cada partida, la suma dels punts de les teves tirades dels teus daus ha de ser superior a la de la CPU")
        ${DAUS}
        
    """)

    // Llegim el número de partides que volem jugar
    do {
        print("Quantes partides vols jugar? (de 1 a 3) ▶ ")
        partides = readLine()?.toIntOrNull()

        if (partides != null && (partides < 1 || partides > 3)){
            partides = null
            println("ERROR: Valor no acceptat!")
        }
    }while(partides == null)

    // Llegim el número de quantes tirades volem fer per cada partida
    do {
        print("Quantes tirades vols fer per cada partida? (de 1 a 6) ▶ ")
        tiradesPerPartida = readLine()?.toIntOrNull()

        if (tiradesPerPartida != null && (tiradesPerPartida < 1 || tiradesPerPartida > 6)){
            tiradesPerPartida = null
            println("ERROR: Valor no acceptat!")
        }
    }while(tiradesPerPartida == null)

    // Declarem la matriu
    var tiradesGuardades: Array<IntArray>

    // Inicialitzem la matriu de partides files i (tiradesPerPartida + 1) columnes
    tiradesGuardades = Array(partides){IntArray((tiradesPerPartida + 1)) }

    // Repetim tantes vegades com partides
    for(partida in 0 until partides) {
        var acumuladorCPU: Int = 0
        var tiradaActual: Int = 0

        println("""
            
            ----------------------------
            PARTIDA ${partida+1}
          
            ---
        """.trimIndent())
        for (tirada in 0 until tiradesGuardades[partida].size - 1) {
            /** Tirades persona **/
            println("Tira el dau! (Intent ${tirada+1})")
            tiradaActual = Random.nextInt(1, 6 + 1)
            println("Has tret un ${CARES_DAU[tiradaActual-1]} ($tiradaActual) !")

            // Guardem la tirada
            tiradesGuardades[partida][tirada] = tiradaActual

            // Acumulem el sumatori a l'última columna de la fila
            tiradesGuardades[partida][tiradesPerPartida] += tiradaActual

            /** Tirades CPU **/
            println("La CPU tira el dau! (Intent ${tirada+1})")
            tiradaCPU = Random.nextInt(1, 6 + 1)
            acumuladorCPU += tiradaCPU
            println("Ha tret un ${CARES_DAU[tiradaCPU-1]} ($tiradaCPU) !")

            println("---------")
        }
        println("--------------------------------------------")
        println("Partida acabada! <(^_^)>")
        println("Tu has aconseguit ${tiradesGuardades[partida][tiradesPerPartida]} punts")
        println("La CPU ha aconseguit $acumuladorCPU punts")

        if (tiradesGuardades[partida][tiradesPerPartida] > acumuladorCPU){
            println("Has guanyat! ＼(＾O＾)／")
            partidasGanadasPerUsuario++
        }else if (tiradesGuardades[partida][tiradesPerPartida] < acumuladorCPU){
            println("Has perdut! (╥﹏╥)")
            partidasGanadasPerCPU++
        }else{
            println("Heu empatat! (ㆆ _ ㆆ)")
        }
        Thread.sleep(2000L)
    }
    val percentageUsuario = String.format("%.2f",(partidasGanadasPerUsuario.toDouble()/partides)*100)
    val percentageCPU = String.format("%.2f",(partidasGanadasPerCPU.toDouble()/partides)*100)
    println("""
        
        
        ════════════════════════════════════
        🎮 Estadístiques de la partida 🎮
        ────────────────────────────────────
        👤 Percentatge de tirades guanyades (Usuari): ${percentageUsuario} %
        🤖 Percentatge de tirades guanyades (CPU): ${percentageCPU} %
        ════════════════════════════════════
    """.trimIndent())
}