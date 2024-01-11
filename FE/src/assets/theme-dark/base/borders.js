// Argon Dashboard 2 MUI Base Styles
import colors from "./colors";

// Argon Dashboard 2 MUI Helper Functions
import pxToRem from "../functions/pxToRem";
import rgba from "../functions/rgba";

const { white } = colors;

const borders = {
  borderColor: rgba(white.main, 0.15),

  borderWidth: {
    0: 0,
    1: pxToRem(1),
    2: pxToRem(2),
    3: pxToRem(3),
    4: pxToRem(4),
    5: pxToRem(5),
  },

  borderRadius: {
    xs: pxToRem(2),
    sm: pxToRem(4),
    md: pxToRem(8),
    lg: pxToRem(12),
    xl: pxToRem(16),
    xxl: pxToRem(24),
    section: pxToRem(160),
  },
};

export default borders;
