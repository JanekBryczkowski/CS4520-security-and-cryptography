public class LFSR {
    // Function to simulate the LFSR and output the first 20 bits
    public static void simulateLFSR(int degree, int[] coefficients, int[] initialState, int steps) {
        int[] state = new int[degree]; // Holds the current state
        System.arraycopy(initialState, 0, state, 0, degree); // Copy initial state to state

        System.out.println("First " + steps + " output bits of the LFSR:");

        for (int step = 0; step < steps; step++) {
            int outputBit = state[degree - 1]; // Output the last bit in the state
            System.out.print(outputBit + " "); // Print the output bit

            // Calculate the new bit using XOR of specified taps
            int newBit = 0;
            for (int i = 0; i < degree; i++) {
                newBit ^= (coefficients[i] * state[i]);
            }
            newBit = newBit % 2; // Ensure bit is 0 or 1

            // Shift the register to the right and insert the new bit
            for (int i = degree - 1; i > 0; i--) {
                state[i] = state[i - 1];
            }
            state[0] = newBit; // Insert the new bit at the start
        }
    }

    public static void main(String[] args) {
        int degree = 10; // Degree of the LFSR
        int[] coefficients = {1, 0, 0, 0, 1, 0, 0, 1, 0, 1}; // Coefficients (c1 to c10)
        int[] initialState = {1, 1, 1, 0, 0, 0, 0, 1, 0, 1};  // Initial state (s9 to s0)

        // Simulate LFSR for 20 steps
        simulateLFSR(degree, coefficients, initialState, 20);
    }
}