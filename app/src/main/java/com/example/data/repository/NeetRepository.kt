package com.example.data.repository

import com.example.data.local.*
import com.example.model.*
import kotlinx.coroutines.flow.Flow

class NeetRepository(private val database: AppDatabase) {

    val studyPlans: Flow<List<StudyPlanEntity>> = database.studyPlanDao().getAllPlans()
    val testResults: Flow<List<TestResultEntity>> = database.testResultDao().getAllResults()
    val bookmarks: Flow<List<BookmarkEntity>> = database.bookmarkDao().getAllBookmarks()
    val communityPosts: Flow<List<CommunityPostEntity>> = database.communityPostDao().getAllPosts()
    val userProfile: Flow<UserProfileEntity?> = database.userProfileDao().getUserProfile()

    suspend fun addPlan(title: String, subject: String, targetDate: String = "Today") {
        database.studyPlanDao().insertPlan(
            StudyPlanEntity(title = title, subject = subject, targetDate = targetDate)
        )
    }

    suspend fun togglePlanCompletion(plan: StudyPlanEntity) {
        database.studyPlanDao().updatePlan(plan.copy(isCompleted = !plan.isCompleted))
    }

    suspend fun deletePlan(plan: StudyPlanEntity) {
        database.studyPlanDao().deletePlan(plan)
    }

    suspend fun saveTestResult(result: TestResultEntity) {
        database.testResultDao().insertResult(result)
    }

    suspend fun addBookmark(title: String, subtitle: String, content: String, subject: String, type: String) {
        database.bookmarkDao().insertBookmark(
            BookmarkEntity(title = title, subtitle = subtitle, content = content, subject = subject, type = type)
        )
    }

    suspend fun deleteBookmark(bookmark: BookmarkEntity) {
        database.bookmarkDao().deleteBookmark(bookmark)
    }

    suspend fun addCommunityPost(author: String, title: String, content: String, subject: String) {
        database.communityPostDao().insertPost(
            CommunityPostEntity(author = author, title = title, content = content, subject = subject)
        )
    }

    suspend fun upvotePost(postId: Long) {
        database.communityPostDao().upvotePost(postId)
    }

    suspend fun updateProfile(profile: UserProfileEntity) {
        database.userProfileDao().saveUserProfile(profile)
    }

    // ================= 35 THEMES DEFINITIONS =================
    val themesList: List<AppThemeData> = listOf(
        AppThemeData("Ocean Blue", "🌊", 0xFF287CFF, 0xFF7C3CFF, 0xFF06122F, 0xFF0B1D46),
        AppThemeData("Purple Dream", "💜", 0xFF8B5CF6, 0xFFEC4899, 0xFF180B2E, 0xFF321052),
        AppThemeData("Cyberpunk", "🤖", 0xFF00F5FF, 0xFFFF00D4, 0xFF050510, 0xFF15152F),
        AppThemeData("Sunset", "🌅", 0xFFFF6B35, 0xFFFF1744, 0xFF30100B, 0xFF541B12),
        AppThemeData("Royal Gold", "👑", 0xFFFBBF24, 0xFFF59E0B, 0xFF18120A, 0xFF30220C),
        AppThemeData("Emerald", "💚", 0xFF10B981, 0xFF22C55E, 0xFF041C16, 0xFF073D2E),
        AppThemeData("Rose", "🌹", 0xFFF43F5E, 0xFFEC4899, 0xFF240914, 0xFF451025),
        AppThemeData("Midnight", "🌑", 0xFF6366F1, 0xFF312E81, 0xFF020617, 0xFF0F172A),
        AppThemeData("Crimson", "❤️", 0xFFEF4444, 0xFF991B1B, 0xFF200506, 0xFF400B0D),
        AppThemeData("Neon Green", "🟢", 0xFF39FF14, 0xFF00C853, 0xFF031405, 0xFF082B0D),
        AppThemeData("Sky", "☁️", 0xFF38BDF8, 0xFF0EA5E9, 0xFF061827, 0xFF0C304A),
        AppThemeData("Aqua", "💧", 0xFF06B6D4, 0xFF14B8A6, 0xFF031B22, 0xFF063942),
        AppThemeData("Violet", "🔮", 0xFFA855F7, 0xFF7E22CE, 0xFF180622, 0xFF321044),
        AppThemeData("Magenta", "💗", 0xFFD946EF, 0xFFEC4899, 0xFF250625, 0xFF48104A),
        AppThemeData("Electric", "⚡", 0xFFFACC15, 0xFF22D3EE, 0xFF10130A, 0xFF18333B),
        AppThemeData("Fire", "🔥", 0xFFF97316, 0xFFDC2626, 0xFF250B03, 0xFF481305),
        AppThemeData("Ice", "❄️", 0xFF67E8F9, 0xFF60A5FA, 0xFF031827, 0xFF0B2F4A),
        AppThemeData("Forest", "🌲", 0xFF16A34A, 0xFF15803D, 0xFF04150A, 0xFF092A13),
        AppThemeData("Lavender", "🪻", 0xFFC084FC, 0xFFA78BFA, 0xFF170D27, 0xFF30204A),
        AppThemeData("Cherry", "🍒", 0xFFFB7185, 0xFFBE123C, 0xFF21070D, 0xFF450E1C),
        AppThemeData("Solar", "☀️", 0xFFF59E0B, 0xFFFACC15, 0xFF1C1204, 0xFF3B2508),
        AppThemeData("Galaxy", "🌌", 0xFF818CF8, 0xFFC084FC, 0xFF05021A, 0xFF180B3B),
        AppThemeData("Space", "🚀", 0xFF60A5FA, 0xFF9333EA, 0xFF020617, 0xFF111827),
        AppThemeData("Neon Pink", "🩷", 0xFFFF1493, 0xFFFF00FF, 0xFF180016, 0xFF39002F),
        AppThemeData("Neon Orange", "🟠", 0xFFFF7A00, 0xFFFF3D00, 0xFF1B0900, 0xFF3D1600),
        AppThemeData("Turquoise", "🩵", 0xFF2DD4BF, 0xFF06B6D4, 0xFF031916, 0xFF063C38),
        AppThemeData("Deep Blue", "🔵", 0xFF2563EB, 0xFF1D4ED8, 0xFF020B24, 0xFF071B4C),
        AppThemeData("Plasma", "⚛️", 0xFFE879F9, 0xFF22D3EE, 0xFF10041C, 0xFF25104A),
        AppThemeData("Matrix", "💻", 0xFF22C55E, 0xFF16A34A, 0xFF020D05, 0xFF06200D),
        AppThemeData("Holographic", "✨", 0xFF67E8F9, 0xFFF0ABFC, 0xFF0A0A20, 0xFF202047),
        AppThemeData("Aurora", "🌈", 0xFF34D399, 0xFF818CF8, 0xFF03131A, 0xFF11254A),
        AppThemeData("Royal Purple", "🔱", 0xFF7C3AED, 0xFFC026D3, 0xFF10051F, 0xFF2B0D46),
        AppThemeData("Neon Blue", "🔷", 0xFF00BFFF, 0xFF0066FF, 0xFF020B18, 0xFF06234D),
        AppThemeData("Dark Red", "🟥", 0xFFFF3333, 0xFF8B0000, 0xFF120303, 0xFF2D0606),
        AppThemeData("Diamond", "💎", 0xFF93C5FD, 0xFFC4B5FD, 0xFF07111F, 0xFF172554)
    )

    // ================= CHAPTERS DATA =================
    val chapters: List<Chapter> = listOf(
        // PHYSICS
        Chapter(
            id = "phy_1",
            title = "Units, Dimensions and Measurements",
            titleHindi = "मात्रक एवं मापन",
            subject = Subject.PHYSICS,
            totalQuestions = 45,
            summary = "Fundamental SI units, dimensional analysis, screw gauge, vernier callipers and error propagation formulas.",
            keyPoints = listOf(
                "Absolute Error Δa = |a - a_mean|",
                "Relative Error = Δa_mean / a_mean",
                "Percentage Error = (Δa_mean / a_mean) × 100%",
                "Principle of Homogeneity: Dimensions on both sides must be equal."
            ),
            formulas = listOf(
                FormulaItem("Least Count Vernier", "LC = 1 MSD - 1 VSD", "Main scale division minus vernier scale division"),
                FormulaItem("Screw Gauge LC", "LC = Pitch / Total Circular Divisions", "Pitch divided by head scale count"),
                FormulaItem("Error Combination for Z = A^p B^q / C^r", "ΔZ/Z = p(ΔA/A) + q(ΔB/B) + r(ΔC/C)", "Power rule in fractional errors")
            ),
            isHighYield = true
        ),
        Chapter(
            id = "phy_2",
            title = "Kinematics & Motion in 1D & 2D",
            titleHindi = "सरल रेखा एवं समतल में गति",
            subject = Subject.PHYSICS,
            totalQuestions = 60,
            summary = "Equations of uniformly accelerated motion, projectile motion trajectory, relative velocity, river-boat problems.",
            keyPoints = listOf(
                "v = u + at, s = ut + 1/2 at², v² = u² + 2as",
                "Time of Flight (Projectile) T = 2u sinθ / g",
                "Maximum Height H = u² sin²θ / 2g",
                "Horizontal Range R = u² sin(2θ) / g; max range at 45°"
            ),
            formulas = listOf(
                FormulaItem("Equation of Trajectory", "y = x tanθ - (gx²)/(2u² cos²θ)", "Parabolic trajectory path equation"),
                FormulaItem("Relative Velocity", "v_AB = v_A - v_B", "Vector subtraction of velocities")
            ),
            isHighYield = true
        ),
        Chapter(
            id = "phy_3",
            title = "Laws of Motion & Friction",
            titleHindi = "गति के नियम एवं घर्षण",
            subject = Subject.PHYSICS,
            totalQuestions = 55,
            summary = "Newton's 3 laws, Free Body Diagrams, static and kinetic friction, banking of roads, pulley systems.",
            keyPoints = listOf(
                "F_net = dp/dt = m(dv/dt) = ma (for constant mass)",
                "Impulse J = F_avg × Δt = Δp",
                "Static Friction f_s ≤ μ_s N, Kinetic Friction f_k = μ_k N",
                "Optimum Banking Speed v = √(rg tanθ)"
            ),
            formulas = listOf(
                FormulaItem("Pulley Acceleration", "a = (m2 - m1)g / (m1 + m2)", "Atwood machine simple tension and acceleration"),
                FormulaItem("Banking Angle", "tanθ = v² / (rg)", "Angle of banking for frictionless curve")
            ),
            isHighYield = true
        ),
        Chapter(
            id = "phy_4",
            title = "Work, Energy & Power",
            titleHindi = "कार्य, ऊर्जा एवं शक्ति",
            subject = Subject.PHYSICS,
            totalQuestions = 50,
            summary = "Work done by variable & constant force, Work-Energy theorem, potential energy of springs, power, collisions.",
            keyPoints = listOf(
                "W = ∫ F · dr = F s cosθ",
                "Work Energy Theorem: W_net = ΔKE = KE_f - KE_i",
                "Spring Potential Energy U = 1/2 k x²",
                "Power P = dW/dt = F · v"
            ),
            formulas = listOf(
                FormulaItem("Coefficient of Restitution", "e = (v2 - v1) / (u1 - u2)", "Velocity of separation / approach"),
                FormulaItem("Conservative Force", "F = - dU/dr", "Gradient of potential energy")
            ),
            isHighYield = true
        ),
        Chapter(
            id = "phy_5",
            title = "Electrostatics & Capacitance",
            titleHindi = "स्थिर वैद्युतिकी एवं धारिता",
            subject = Subject.PHYSICS,
            totalQuestions = 70,
            summary = "Coulomb's Law, Electric field and potential, Gauss's theorem, parallel plate capacitor, dielectric insertion.",
            keyPoints = listOf(
                "Coulomb Force F = (1 / 4πε₀) (q1 q2 / r²)",
                "Electric Field E = - dV/dr",
                "Capacitance C = ε₀ A / d; with dielectric C' = K C",
                "Energy Stored U = 1/2 C V² = Q² / (2C)"
            ),
            formulas = listOf(
                FormulaItem("Gauss Law", "Φ = ∮ E · dA = Q_enclosed / ε₀", "Total electric flux through closed surface"),
                FormulaItem("Electric Dipole Field", "E_axial = 2kp/r³, E_equatorial = kp/r³", "Far field approximation for electric dipole")
            ),
            isHighYield = true
        ),
        Chapter(
            id = "phy_6",
            title = "Optics: Ray and Wave",
            titleHindi = "प्रकाशिकी (किरण एवं तरंग)",
            subject = Subject.PHYSICS,
            totalQuestions = 65,
            summary = "Snell's Law, Total Internal Reflection, Lens Maker formula, Prism dispersion, Young's Double Slit Experiment.",
            keyPoints = listOf(
                "Mirror Formula: 1/f = 1/v + 1/u, Lens Formula: 1/f = 1/v - 1/u",
                "Lens Maker: 1/f = (μ - 1) (1/R1 - 1/R2)",
                "YDSE Fringe Width β = λ D / d",
                "Brewster's Law: tan(i_p) = μ"
            ),
            formulas = listOf(
                FormulaItem("Prism Formula", "μ = sin((A + δ_m)/2) / sin(A/2)", "Refractive index in minimum deviation position"),
                FormulaItem("YDSE Bright Fringe", "y_n = n λ D / d", "Position of nth maxima from central fringe")
            ),
            isHighYield = true
        ),

        // CHEMISTRY
        Chapter(
            id = "chem_1",
            title = "Some Basic Concepts of Chemistry (Mole Concept)",
            titleHindi = "रसायन विज्ञान की कुछ मूल अवधारणाएँ",
            subject = Subject.CHEMISTRY,
            totalQuestions = 50,
            summary = "Moles, molar mass, stoichiometry, limiting reagent, molarity, molality, normality, empirical formula.",
            keyPoints = listOf(
                "Number of Moles n = Given Mass / Molar Mass = N / N_A = Volume (STP) / 22.4 L",
                "Molarity M = Moles of Solute / Volume of Solution (L)",
                "Molality m = Moles of Solute / Mass of Solvent (kg)",
                "Normality N = Molarity × n-factor"
            ),
            formulas = listOf(
                FormulaItem("Molarity Equation", "M1 V1 = M2 V2", "Dilution formula"),
                FormulaItem("Mole Fraction", "X_A = n_A / (n_A + n_B)", "Fraction of moles of component A")
            ),
            isHighYield = true
        ),
        Chapter(
            id = "chem_2",
            title = "Structure of Atom & Periodic Classification",
            titleHindi = "परमाणु संरचना एवं आवर्त सारणी",
            subject = Subject.CHEMISTRY,
            totalQuestions = 60,
            summary = "Bohr model, de Broglie relation, Heisenberg uncertainty, quantum numbers, periodic trends (IE, EA, radius).",
            keyPoints = listOf(
                "Energy of Bohr Orbit E_n = -13.6 Z² / n² eV",
                "de Broglie wavelength λ = h / mv = h / √(2mE)",
                "Heisenberg: Δx · Δp ≥ h / (4π)",
                "Ionization Energy increases across period, decreases down group."
            ),
            formulas = listOf(
                FormulaItem("Rydberg Formula", "1/λ = R_H Z² (1/n1² - 1/n2²)", "Wavelength of spectral lines"),
                FormulaItem("Orbital Angular Momentum", "L = √(l(l+1)) · h/(2π)", "Quantum mechanical angular momentum")
            ),
            isHighYield = true
        ),
        Chapter(
            id = "chem_3",
            title = "Chemical Bonding & Molecular Structure",
            titleHindi = "रासायनिक आबंधन एवं आण्विक संरचना",
            subject = Subject.CHEMISTRY,
            totalQuestions = 65,
            summary = "VSEPR theory, Hybridization, Dipole moment, Molecular Orbital Theory (MOT), Bond order, Hydrogen bonding.",
            keyPoints = listOf(
                "Hybridization steric number = (Valence electrons on central atom + Monovalent atoms - Charge) / 2",
                "Bond Order = 1/2 (N_b - N_a); if Bond Order > 0, molecule is stable",
                "Paramagnetism occurs when unpaired electrons exist in molecular orbitals (e.g., O2, B2)",
                "Electronegativity order: F > O > N ≈ Cl > Br > I"
            ),
            formulas = listOf(
                FormulaItem("Dipole Moment", "μ = q × d (in Debye)", "Measure of net molecular polarity"),
                FormulaItem("Bond Order Formula", "BO = (N_bonding - N_antibonding) / 2", "Molecular orbital theory bond multiplicity")
            ),
            isHighYield = true
        ),
        Chapter(
            id = "chem_4",
            title = "General Organic Chemistry (GOC)",
            titleHindi = "सामान्य कार्बनिक रसायन (GOC)",
            subject = Subject.CHEMISTRY,
            totalQuestions = 75,
            summary = "Inductive effect, Resonance/Mesomeric, Hyperconjugation, Electrophiles/Nucleophiles, Carbocation & Carbanion stability, Acidity & Basicity.",
            keyPoints = listOf(
                "Carbocation stability: 3° > 2° > 1° > methyl (governed by +I and hyperconjugation)",
                "Carbanion stability: 1° > 2° > 3° (governed by -I and s-character)",
                "Acidity ∝ -M, -I, +conjugate base stability",
                "Basicity ∝ +M, +I, steric hindrance (in aqueous: 2° > 1° > 3° for ethyl/methyl)"
            ),
            formulas = listOf(
                FormulaItem("Hückel's Rule", "Aromatic if cyclic, planar, fully conjugated with (4n + 2) π electrons", "Criteria for aromaticity"),
                FormulaItem("Hyperconjugation Count", "Stability ∝ number of α-hydrogens", "Count C-H bonds adjacent to sp2 carbon")
            ),
            isHighYield = true
        ),

        // BIOLOGY
        Chapter(
            id = "bio_1",
            title = "Cell: The Unit of Life & Cell Cycle",
            titleHindi = "कोशिका: जीवन की इकाई एवं कोशिका चक्र",
            subject = Subject.BIOLOGY,
            totalQuestions = 70,
            summary = "Prokaryotic vs Eukaryotic cells, Fluid Mosaic Model, Endomembrane system, Mitochondria, Mitosis & Meiosis stages (Crossing over in Pachytene).",
            keyPoints = listOf(
                "Cell Theory proposed by Schleiden & Schwann; modified with 'Omnis cellula-e cellula' by Rudolf Virchow (1855).",
                "Fluid mosaic model by Singer and Nicolson (1972) - quasi-fluid nature of lipids enables lateral protein movement.",
                "Meiosis I Prophase I substages: Leptotene → Zygotene (synaptonemal complex) → Pachytene (crossing over via recombinase) → Diplotene (chiasmata) → Diakinesis (terminalisation).",
                "Mitochondria and Chloroplast are semi-autonomous organelles with 70S ribosomes and circular DNA."
            ),
            isHighYield = true
        ),
        Chapter(
            id = "bio_2",
            title = "Genetics & Molecular Basis of Inheritance",
            titleHindi = "आनुवंशिकी एवं वंशागति का आण्विक आधार",
            subject = Subject.BIOLOGY,
            totalQuestions = 80,
            summary = "Mendelian genetics, Incomplete dominance, Linkage, DNA structure (Watson-Crick), Replication (Meselson-Stahl), Transcription, Genetic code, Translation, Lac Operon.",
            keyPoints = listOf(
                "Monohybrid phenotypic ratio: 3:1, genotypic ratio: 1:2:1; Dihybrid ratio: 9:3:3:1.",
                "DNA replication is semi-conservative (proved by Meselson & Stahl using 15N in E. coli).",
                "Genetic code is universal, degenerate, non-overlapping, and comma-less. AUG codes for Methionine (initiator codon).",
                "Lac Operon: Repressor protein binds to Operator; Lactose (allolactose) acts as the inducer."
            ),
            isHighYield = true
        ),
        Chapter(
            id = "bio_3",
            title = "Human Physiology: Digestion to Endocrine",
            titleHindi = "मानव कार्यिकी (पाचन से अंतःस्रावी तंत्र)",
            subject = Subject.BIOLOGY,
            totalQuestions = 90,
            summary = "Human breathing mechanism, cardiac cycle, ECG, nephron counter-current mechanism, sliding filament theory of muscle contraction, nerve impulse transmission, hormones.",
            keyPoints = listOf(
                "Cardiac output = Stroke volume (70 mL) × Heart rate (72 bpm) ≈ 5000 mL/min.",
                "ECG waves: P wave (atrial depolarisation), QRS complex (ventricular depolarisation), T wave (ventricular repolarisation).",
                "Juxtaglomerular Apparatus (JGA) secretes Renin in response to fall in GFR → activates RAAS pathway.",
                "Insulin is secreted by β-cells of islets of Langerhans; Glucagon by α-cells."
            ),
            isHighYield = true
        ),
        Chapter(
            id = "bio_4",
            title = "Ecology & Environment",
            titleHindi = "पारिस्थितिकी एवं पर्यावरण",
            subject = Subject.BIOLOGY,
            totalQuestions = 60,
            summary = "Population interactions (Mutualism, Commensalism, Parasitism), Ecological pyramids, Carbon & Phosphorus cycles, Biodiversity hotspots, in-situ vs ex-situ conservation.",
            keyPoints = listOf(
                "Energy pyramid is ALWAYS upright (10% energy transfer law by Lindeman).",
                "Mutualism (+,+), Commensalism (+,0), Amensalism (-,0), Parasitism (+,-).",
                "Biodiversity conservation: In-situ (National Parks, Sanctuaries, Biosphere Reserves) vs Ex-situ (Botanical Gardens, Zoological Parks, Cryopreservation).",
                "Alexander von Humboldt gave Species-Area relationship: log S = log C + Z log A."
            ),
            isHighYield = true
        )
    )

    // ================= QUESTION BANK DATA =================
    val questionsList: List<MCQQuestion> = listOf(
        MCQQuestion(
            id = "q1",
            questionHindi = "पादप कोशिका में ग्लाइकोलाइसिस (Glycolysis) की प्रक्रिया कोशिका के किस भाग में सम्पन्न होती है?",
            questionEnglish = "In a plant cell, glycolysis takes place in which of the following cellular compartments?",
            subject = Subject.BIOLOGY,
            chapter = "Plant Physiology",
            options = listOf(
                "A) माइटोकॉन्ड्रिया मैट्रिक्स (Mitochondrial Matrix)",
                "B) कोशिकाद्रव्य (Cytoplasm / Cytosol)",
                "C) क्लोरोप्लास्ट स्ट्रोमा (Chloroplast Stroma)",
                "D) गॉल्जी काय (Golgi Body)"
            ),
            correctIndex = 1,
            explanation = "Glycolysis (EMP pathway) occurs in the cytoplasm of all living cells, converting 1 molecule of glucose into 2 molecules of pyruvic acid without requiring oxygen.",
            yearTag = "NEET PYQ 2023"
        ),
        MCQQuestion(
            id = "q2",
            questionHindi = "यदि किसी उपग्रह की पृथ्वी की सतह से ऊँचाई शून्य मानी जाए, तो कक्षीय वेग (Orbital Velocity) v₀ और पलायन वेग (Escape Velocity) v_e में क्या संबंध होता है?",
            questionEnglish = "What is the relation between orbital velocity (v₀) and escape velocity (v_e) near the surface of Earth?",
            subject = Subject.PHYSICS,
            chapter = "Gravitation",
            options = listOf(
                "A) v_e = 2 v₀",
                "B) v_e = √2 · v₀",
                "C) v_e = v₀ / √2",
                "D) v_e = v₀² / 2"
            ),
            correctIndex = 1,
            explanation = "Escape velocity v_e = √(2gR) and orbital velocity near surface v₀ = √(gR). Hence v_e = √2 · v₀ ≈ 1.414 v₀.",
            yearTag = "NEET PYQ 2022"
        ),
        MCQQuestion(
            id = "q3",
            questionHindi = "निम्नलिखित में से कौन-सा अणु अनुचुंबकीय (Paramagnetic) प्रकृति का होता है?",
            questionEnglish = "According to Molecular Orbital Theory, which of the following diatomic species is paramagnetic?",
            subject = Subject.CHEMISTRY,
            chapter = "Chemical Bonding",
            options = listOf(
                "A) N₂",
                "B) C₂",
                "C) O₂",
                "D) F₂"
            ),
            correctIndex = 2,
            explanation = "O₂ contains 16 electrons. Its electronic configuration in MOT puts 2 unpaired electrons in degenerate antibonding π*2px and π*2py orbitals, making it paramagnetic.",
            yearTag = "NEET PYQ 2024"
        ),
        MCQQuestion(
            id = "q4",
            questionHindi = "अर्धसूत्री विभाजन (Meiosis) के किस उप-चरण में समजात गुणसूत्रों के बीच क्रॉसिंग ओवर (Crossing Over) होती है?",
            questionEnglish = "During which stage of Meiosis I does crossing over between homologous chromosomes take place?",
            subject = Subject.BIOLOGY,
            chapter = "Cell Cycle & Cell Division",
            options = listOf(
                "A) लेप्टोटीन (Leptotene)",
                "B) जाइगोटीन (Zygotene)",
                "C) पैकीटीन (Pachytene)",
                "D) डिप्लोटीन (Diplotene)"
            ),
            correctIndex = 2,
            explanation = "Crossing over is an enzyme-mediated process (enzyme recombinase) that occurs specifically in the Pachytene stage of Prophase I.",
            yearTag = "NEET PYQ 2023"
        ),
        MCQQuestion(
            id = "q5",
            questionHindi = "एक कण 10 m त्रिज्या के वृत्ताकार पथ पर 5 m/s की नियत चाल से घूम रहा है। कण का अभिकेंद्रीय त्वरण (Centripetal Acceleration) क्या होगा?",
            questionEnglish = "A particle moves in a circle of radius 10 m at a constant speed of 5 m/s. What is its centripetal acceleration?",
            subject = Subject.PHYSICS,
            chapter = "Kinematics & Circular Motion",
            options = listOf(
                "A) 2.5 m/s²",
                "B) 5.0 m/s²",
                "C) 0.5 m/s²",
                "D) 25 m/s²"
            ),
            correctIndex = 0,
            explanation = "Centripetal acceleration a_c = v² / r = (5)² / 10 = 25 / 10 = 2.5 m/s² directed towards the center.",
            yearTag = "NEET 2021"
        ),
        MCQQuestion(
            id = "q6",
            questionHindi = "सॉल्यूशन की नॉर्मलता (Normality) और मोलरता (Molarity) में 0.1 M H₂SO₄ के लिए क्या संबंध होगा?",
            questionEnglish = "What is the normality of a 0.1 M aqueous solution of H₂SO₄?",
            subject = Subject.CHEMISTRY,
            chapter = "Mole Concept & Solutions",
            options = listOf(
                "A) 0.05 N",
                "B) 0.1 N",
                "C) 0.2 N",
                "D) 0.4 N"
            ),
            correctIndex = 2,
            explanation = "H₂SO₄ is a dibasic acid, so n-factor = 2. Normality = Molarity × n-factor = 0.1 × 2 = 0.2 N.",
            yearTag = "NEET PYQ 2022"
        ),
        MCQQuestion(
            id = "q7",
            questionHindi = "मानव हृदय के ईसीजी (ECG) में QRS सम्मिश्र (QRS Complex) क्या निरूपित करता है?",
            questionEnglish = "In a standard ECG, what does the QRS complex represent?",
            subject = Subject.BIOLOGY,
            chapter = "Human Physiology",
            options = listOf(
                "A) आलिंदों का विध्रुवण (Atrial Depolarisation)",
                "B) निलयों का विध्रुवण (Ventricular Depolarisation)",
                "C) निलयों का पुनर्ध्रुवण (Ventricular Repolarisation)",
                "D) आलिंदों का संकुचन विराम"
            ),
            correctIndex = 1,
            explanation = "The QRS complex represents ventricular depolarisation, which initiates the contraction of ventricles (ventricular systole).",
            yearTag = "NEET PYQ 2024"
        ),
        MCQQuestion(
            id = "q8",
            questionHindi = "एक समान्तर प्लेट संधारित्र (Parallel Plate Capacitor) की प्लेटों के बीच परावैद्युतांक K वाला माध्यम भरने पर उसकी धारिता (Capacitance) पर क्या प्रभाव पड़ेगा?",
            questionEnglish = "What happens to the capacitance of a parallel plate capacitor when a dielectric of constant K is inserted between the plates?",
            subject = Subject.PHYSICS,
            chapter = "Electrostatics & Capacitance",
            options = listOf(
                "A) K गुना बढ़ जाती है (Increases K times)",
                "B) K गुना घट जाती है (Decreases K times)",
                "C) अपरिवर्तित रहती है (Remains unchanged)",
                "D) शून्य हो जाती है (Becomes zero)"
            ),
            correctIndex = 0,
            explanation = "Capacitance with dielectric C' = K · C₀. Since K > 1 for all dielectric materials, the capacitance increases by a factor of K.",
            yearTag = "NEET 2023"
        )
    )

    // ================= FULL MOCK & CHAPTER TESTS =================
    val mockTests: List<TestExam> = listOf(
        TestExam(
            id = "mock_full_1",
            title = "Full NEET Mock Test #1 (Grand PCB Simulation)",
            type = "Full Mock",
            durationMinutes = 200,
            totalQuestions = 180,
            questions = questionsList,
            instructions = "Standard NEET 2027 marking scheme: +4 for correct answer, -1 for incorrect answer, 0 for unattempted. Total 720 marks."
        ),
        TestExam(
            id = "mock_bio_speed",
            title = "Biology Rapid Fire Speed Test",
            type = "Speed Test",
            durationMinutes = 30,
            totalQuestions = 45,
            questions = questionsList.filter { it.subject == Subject.BIOLOGY },
            instructions = "High speed test for NCERT Biology concepts. Aim for 30 questions in 20 minutes!"
        ),
        TestExam(
            id = "mock_phy_chap",
            title = "Physics Mechanics & Electrostatics Chapter Test",
            type = "Chapter Test",
            durationMinutes = 45,
            totalQuestions = 30,
            questions = questionsList.filter { it.subject == Subject.PHYSICS },
            instructions = "Focus on numerical formulas, error analysis, and vector components."
        ),
        TestExam(
            id = "mock_omr_1",
            title = "NEET OMR Bubble Sheet Practice Test",
            type = "OMR Test",
            durationMinutes = 60,
            totalQuestions = 45,
            questions = questionsList,
            instructions = "Fill bubbles on the virtual OMR sheet just like the real NTA NEET paper."
        )
    )

    // ================= DAILY PRACTICE PROBLEMS (DPP) =================
    val dailyDPPs: List<DPPItem> = listOf(
        DPPItem(
            id = "dpp_bio_day1",
            dayNumber = 1,
            title = "DPP Day 1: Cell Biology & Biomolecules",
            subject = Subject.BIOLOGY,
            questions = questionsList.filter { it.subject == Subject.BIOLOGY },
            isCompleted = false
        ),
        DPPItem(
            id = "dpp_phy_day1",
            dayNumber = 1,
            title = "DPP Day 1: Units, Vectors & Kinematics",
            subject = Subject.PHYSICS,
            questions = questionsList.filter { it.subject == Subject.PHYSICS },
            isCompleted = false
        ),
        DPPItem(
            id = "dpp_chem_day1",
            dayNumber = 1,
            title = "DPP Day 1: Mole Concept & Atomic Structure",
            subject = Subject.CHEMISTRY,
            questions = questionsList.filter { it.subject == Subject.CHEMISTRY },
            isCompleted = true,
            score = 24
        ),
        DPPItem(
            id = "dpp_bio_day2",
            dayNumber = 2,
            title = "DPP Day 2: Genetics & Molecular Inheritance",
            subject = Subject.BIOLOGY,
            questions = questionsList.filter { it.subject == Subject.BIOLOGY },
            isCompleted = false
        )
    )

    // ================= STUDY WEBSITES FROM ORIGINAL APP =================
    val studyWebsites: List<StudyWebsiteItem> = listOf(
        StudyWebsiteItem("StudyBee Pro", "http://studybeepro.site/", "High-yield NEET notes, video summaries and question banks", "Notes & Portal", "🐝"),
        StudyWebsiteItem("RolexCoderZ", "http://RolexCoderZ.in/", "NEET study portal and interactive student utilities", "Resources", "⚡"),
        StudyWebsiteItem("VedStudy", "https://vedstudy.com/", "Comprehensive NCERT Hindi and English study material", "NCERT Library", "📚"),
        StudyWebsiteItem("PrepPro Network", "https://preppronetwork.vercel.app/", "NEET mock tests and question paper archives", "Mock Tests", "🎯"),
        StudyWebsiteItem("StudyPanda Books", "https://studypanda.live/books", "Free medical entrance reference books & guides", "Books", "🐼"),
        StudyWebsiteItem("Learnify", "https://learnify.deltaverse.site/", "Modern digital study room and peer tools", "Digital Room", "💡"),
        StudyWebsiteItem("PW StudyParcham", "https://pw.studyparcham.in/", "Lecture slides and practice DPP PDFs", "DPP PDFs", "📝"),
        StudyWebsiteItem("AS Multiverse", "https://asmultiverse.com/", "NEET 2027 mega portal and test series links", "Test Series", "🌌"),
        StudyWebsiteItem("StudyRays", "http://studyrays.cc/", "Fast reference summaries and flashcards", "Flashcards", "☀️"),
        StudyWebsiteItem("LearnTopper", "http://learntopper.in/", "Toppers' formula sheets and handwritten notes", "Topper Notes", "🏆"),
        StudyWebsiteItem("StudySpark", "http://studyspark.pro/", "Quick revision micro-modules for PCB", "Revision", "✨")
    )

    // ================= VIDEO LECTURES =================
    val videoLectures: List<VideoLectureItem> = listOf(
        VideoLectureItem("v1", "NEET Physics One Shot: Kinematics & Laws of Motion (Hindi)", "Physics Wallah", Subject.PHYSICS, "Mechanics", "2h 45m", "NEET Physics Hindi Kinematics One Shot"),
        VideoLectureItem("v2", "Electrostatics & Capacitance Complete Chapter (Hindi)", "Unacademy NEET", Subject.PHYSICS, "Electrostatics", "3h 15m", "NEET Physics Electrostatics Hindi One Shot"),
        VideoLectureItem("v3", "General Organic Chemistry (GOC) Masterclass (Hindi)", "Vedantu NEET Made Ejee", Subject.CHEMISTRY, "Organic Chemistry", "2h 30m", "NEET Chemistry GOC Hindi Full Chapter"),
        VideoLectureItem("v4", "Chemical Bonding & Molecular Structure Full Concept", "Mohit Tyagi / Competishun", Subject.CHEMISTRY, "Inorganic", "3h 00m", "Chemical Bonding NEET Hindi One Shot"),
        VideoLectureItem("v5", "Cell Cycle & Cell Division Line-by-Line NCERT (Hindi)", "Dr. Anand Mani", Subject.BIOLOGY, "Cytology", "1h 50m", "Cell Cycle and Cell Division NEET Biology NCERT Hindi"),
        VideoLectureItem("v6", "Molecular Basis of Inheritance (DNA & Replication)", "Garima Goel", Subject.BIOLOGY, "Genetics", "2h 20m", "Molecular Basis of Inheritance NEET Biology Hindi")
    )

    // ================= ACHIEVEMENTS =================
    val achievements: List<AchievementBadge> = listOf(
        AchievementBadge("ach_streak_7", "7-Day Study Streak", "Studied consistently for 7 consecutive days", "🔥", true, 7, 7),
        AchievementBadge("ach_target_10", "Target Crusher", "Completed 10 daily study targets", "🎯", true, 8, 10),
        AchievementBadge("ach_test_master", "Test Master", "Attempted 5 full mock tests", "🏅", false, 2, 5),
        AchievementBadge("ach_bio_guru", "Biology Guru", "Achieved 90%+ in 3 Biology tests", "🧬", true, 3, 3),
        AchievementBadge("ach_accuracy_ace", "Accuracy Ace", "Maintained 85%+ accuracy across 50 questions", "🏹", false, 42, 50),
        AchievementBadge("ach_streak_30", "30-Day Master", "Maintain continuous 30-day streak", "👑", false, 5, 30)
    )
}
